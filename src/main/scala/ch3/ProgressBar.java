package ch3;

import java.text.DecimalFormat;

public class ProgressBar {
    
    private long maxValue;
    private long barLength;
    private char character;
    private DecimalFormat formatter = new DecimalFormat("#.#%");

    public ProgressBar(long maxValue, long barLength, char character) {
        this.maxValue = maxValue;
        this.barLength = barLength;
        this.character = character;
    }

    public void update(long value) {
        if (value < 0 || value > maxValue) {
            throw new IllegalArgumentException();
        }
        
        float percentage = ((float)value / (float)maxValue);

        draw(percentage);
    }

    private void draw(float percentage) {
        System.out.print('\r');
        long numChars = (long) (percentage * barLength);
        for (int i = 0; i < numChars; i++) {
            System.out.print(character);
        }
        System.out.print(' ');
        System.out.print(formatter.format(percentage));
    }

    public void complete() {
        draw(1f);
        System.out.print('\n');
    }
}