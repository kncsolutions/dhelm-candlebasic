/**
 *Copyright 2017 Pallav Nandi Chaudhuri@Knc Solutions Private Limited

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
 *According to litarature a  Black spinning top candle is made of Black body, i.e closing
 *price is lower than the opening price.There must be one shadow present. One of the shadows must
 *be longer than the body. The candle can appear as a long or short candle.
* The goal of this class is to calculate if a candle
 *is   Black spinning top candle or not.   
 */
 public class BlackSpinningTop{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefLength;
   private double Percentage;
   private boolean isBlackSpinningTop;
   private boolean isLongBlackSpinningTop;
   /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numLen : Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
  *@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short. 
  *@throws DataException if sufficient data not available.
  */
   public BlackSpinningTop(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numLen,double percentage) throws DataException{
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
	   BlackSpinningTop();
	 }
   }
    /*
   *
   */
   private void BlackSpinningTop()throws DataException{
	   double avgCandleLength=CandleFacts.getAverageLength(High,Low,RefLength); 
	   if(Open.get(0)>Close.get(0)){         
          if((High.get(0)!=Open.get(0)) || (Low.get(0)!=Close.get(0))){
		     if((High.get(0)-Open.get(0))>(Open.get(0)-Close.get(0)) || (Close.get(0)-Low.get(0))>(Open.get(0)-Close.get(0))){
			   if((High.get(0)-Low.get(0))>avgCandleLength*Percentage/100){
			     if((High.get(0)-Open.get(0))<3*(Open.get(0)-Close.get(0)) &&(Close.get(0)-Low.get(0))<3*(Open.get(0)-Close.get(0))){
			       isBlackSpinningTop=true;
				   isLongBlackSpinningTop=true;
				 }
			   }
			   else if((High.get(0)-Low.get(0))<avgCandleLength*Percentage/100){
			     isBlackSpinningTop=true;
				 isLongBlackSpinningTop=false;
			   }
			 }
		  }
	   }
	}
   /**
   *@return Returns true if the latest candle is a  Black spinning top candle; otherwise returns false. 
   */
   public boolean isBlackSpinningTop(){
     return isBlackSpinningTop;
   }
   /**
   *@return Returns true if the latest candle is long Black spinning top candle; if short candle returns false. 
   */
   public boolean isLongBlackSpinningTop(){
     return isLongBlackSpinningTop;
   }
 }