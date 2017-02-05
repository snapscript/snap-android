package org.snapscript.android.game;

public class SampleAverager {

   private float maximum;
   private float minimum;
   private float count;
   private float sum;

   public SampleAverager() {
      this.minimum = Float.MAX_VALUE;
      this.maximum = Float.MIN_VALUE;
   }

   public float sum() {
      return sum;
   }

   public float count() {
      return count;
   }

   public float maximum() {
      return maximum;
   }

   public float minimum() {
      return minimum;
   }

   public float average() {
      if (count > 0) {
         return sum / count;
      }
      return 0;
   }

   public void reset() {
      minimum = Long.MAX_VALUE;
      maximum = Long.MIN_VALUE;
      count = 0;
      sum = 0;
   }
   
   public void reset(float average) {
      minimum = average;
      maximum = average;
      sum = average;
      count = 1;
   }

   public void sample(float sample) {
      if (sample > maximum) {
         maximum = sample;
      }
      if (sample < minimum) {
         minimum = sample;
      }
      sum += sample;
      count++;
   }
}
