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
 *According to literature a  white marubozu candle is made of white body, i.e closing
 *price is higher than the opening price.There are no upper and lowe shadows.
 *It means the high and low prices are equal to close and open prices respectively.
 * It  appears as a long or short candle. The goal of this class is to calculate if a candle
 *is  white marubozu candle or not.   
 */
 public class WhiteMarubozu{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefBody;
   private int RefLength;
   private double Percentage;
   private boolean isWhiteMarubozu;
   /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@throws DataException if sufficient data not available.
  */
   public WhiteMarubozu(List<Double> open,List<Double> high,List<Double> low,List<Double> close) throws DataException{
     Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 if(Open.size()==0
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   WhiteMarubozu();
	 }
   }
    /*
   *
   */
   private void WhiteMarubozu()throws DataException{
       double avgBodyLength=CandleFacts.getAverageLength(Open,Close,RefBody); 
	   double avgCandleLength=CandleFacts.getAverageLength(High,Low,RefLength); 
	   if(Open.get(0)<Close.get(0)){         
          if((High.get(0)==Close.get(0))&&(Low.get(0)==Open.get(0))){            
                isWhiteMarubozu=true; 				  
          }
	   }
	}
   /**
   *@return Returns true if the latest candle is a white Marubozu candle; otherwise returns false. 
   */
   public boolean isWhiteMarubozu(){
     return isWhiteMarubozu;
   }
   /**
   *@param numLen :  Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
   *@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short. 
   *@return Returns true if the latest candle is long white Marubozu candle; if short candle returns false. 
   *@throws DataException if sufficient data not available.
   */
   public boolean isLongWhiteMarubozu(int numLen,double percentage)throws DataException{
   double avgCandleLength=CandleFacts.getAverageLength(High,Low,RefLength);
   if(isWhiteMarubozu && (High.get(0)-Low.get(0))>avgCandleLength*Percentage/100)
     return true;
    return false;
   }
 }