/**
 *Copyright 2018 Knc Solutions Private Limited

  *Licensed under the Apache License, Version 2.0 (the "License");
  *you may not use this file except in compliance with the License.
  *You may obtain a copy of the License at

  * http://www.apache.org/licenses/LICENSE-2.0

  *Unless required by applicable law or agreed to in writing, software
  *distributed under the License is distributed on an "AS IS" BASIS,
  *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *See the License for the specific language governing permissions and
  *limitations under the License.
 */
 package in.kncsolutions.dhelm.candlebasic;
 
 import java.util.*; 
 import in.kncsolutions.dhelm.exceptions.DataException;
 import in.kncsolutions.dhelm.mathcal.CandleFacts;
 /**
 *According to litarature a  DragonFly Doji candle is a candle where opening and closing prices are equal.
 *Low price is equal to the opening/closing price and high price is dintinct. It appears as a long candle.
 * The goal of this class is to calculate if a candle
 *is   DragonFly Doji candle or not.   
 */
 public class DragonFlyDoji{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefLength;
   private double Percentage;
   private boolean isDragonFlyDoji=false;
  /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numLen : Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
  *@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short. 
  *@throws DataException if sufficient data not available.
  */
   public DragonFlyDoji(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numLen,double percentage)throws DataException{
     Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 RefLength=numLen;
	 Percentage=percentage;
	  if(Open.size()<RefLength
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   DragonFlyDoji();
	 }
   }
    /*
   *
   */
   private void DragonFlyDoji()throws DataException{
     double avgCandleLength=CandleFacts.getAverageLength(High,Low,RefLength); 
     if(Open.get(0)==Close.get(0)){
	   if((Open.get(0)==High.get(0))&& (Close.get(0)>Low.get(0))
	      && (High.get(0)-Low.get(0))>avgCandleLength*Percentage/100){
	     isDragonFlyDoji=true;
	   }
	 }
   }
   /**
   *@return true if the latest candle is a DragonFly Doji Candle
   */
   public boolean isDragonFlyDoji(){
   return isDragonFlyDoji;
   }
 }