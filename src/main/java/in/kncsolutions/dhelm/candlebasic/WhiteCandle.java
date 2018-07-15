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
 *According to literature a  white candle is made of white body, i.e closing
 *price is higher than the opening price.The upper and lower shadows are required.
 *It means the high and low prices cannot be equal to close and open prices respectively.
 *None of the shadows can be greater than the body. It  appears as a long
 *candle. It means that the length of the candle including the shadow must be higher than the
 *average candle length of certain previous candles. The goal of this class is to calculate if a candle
 *is  white candle or not.   
 */
 public class WhiteCandle{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefBody;
   private int RefLength;
   private double Percentage;
   private boolean isWhiteCandle;
   /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numBody : Number of previous periods w.r.t which it is to be found that if the latest candle's body  is greater than the average body length of those past consecutive periods or not. 
  *@param numLen :  Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
  *@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short. 
  *@throws DataException if sufficient data not available.
  */
   public WhiteCandle(List<Double> open,List<Double> high,List<Double> low,List<Double> close, int numBody , int numLen, double percentage) throws DataException{
     Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 RefBody=numBody;
	 RefLength=numLen;
	 Percentage=percentage;
	 isWhiteCandle=false;
	 if(Open.size()<((RefBody>RefLength) ? RefBody : RefLength)
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   WhiteCandle();
	 }
   }
    /*
   *
   */
   private void WhiteCandle()throws DataException{
       double avgBodyLength=CandleFacts.getAverageLength(Open,Close,RefBody); 
	   double avgCandleLength=CandleFacts.getAverageLength(High,Low,RefLength); 
	   if(Open.get(0)<Close.get(0)){         
          if((High.get(0)!=Close.get(0))&&(Low.get(0)!=Open.get(0))){
            if(((High.get(0)-Close.get(0))<(Close.get(0)-Open.get(0))) &&  ((Open.get(0)-Low.get(0))<(Close.get(0)-Open.get(0))) ){
                if((Close.get(0)-Open.get(0))<3*avgBodyLength && (High.get(0)-Low.get(0))>avgCandleLength*Percentage/100){
                   isWhiteCandle=true; 				  
                }
            }
          }
	   }
	}
   /**
   *@return Returns true if the latest candle is long white candle; otherwise returns false. 
   */
   public boolean isWhiteCandle(){
     return isWhiteCandle;
   }
 }