package com.example.mypianobyahtl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.billthefarmer.mididriver.MidiDriver;

public class MainActivity extends AppCompatActivity implements MidiDriver.OnMidiStartListener, View.OnTouchListener {
    //added the implements MidiDriver to AppCompactActivity and OnTouchListener
    private MidiDriver mididr; //defining a MidiDriver
    private byte[] evnt; //defining a evnt byte array variable
    private int[] config; //defining a config int array variable

    //MIDI - music instrument digital interface - can be founded large number of messages which helps to interact with large number of audio instruments
    //import MIDI module to the application package
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //make the application full screen
        setContentView(R.layout.activity_main);

        mididr = new MidiDriver(); //Instantiate the driver
        mididr.setOnMidiStartListener(this); //set the listener, which is implemented as OnMidiStartListener

    }

    @Override
    protected void onResume() { //method onResume
        super.onResume(); //onResume is added first because midi driver uses system resources therefore the process must be started before midi driver started.
        mididr.start(); //starting the midi driver
        config = mididr.config(); //print out the details
    }


    @Override
    protected void onPause() { //method onPause for stopping the processing
        mididr.stop(); //stopping the midi driver, releasing the driver
        super.onPause(); // onPause must be coded after stopping midi driver because it does not need to save the statuses of midi driver by onPause - best practices
    }

    public void keyPressed(View view){ //method on button click - android:onClick="keyPressed"
        if(view.getTag().equals("C")){ //call the same keyPressed and the tag of the button
            playNote(0); // passing the value of offset
        }
        if(view.getTag().equals("Dm")){
            playNote(1);
        }
        if(view.getTag().equals("D")){
            playNote(2);
        }
        if(view.getTag().equals("Em")){
            playNote(3);
        }
        if(view.getTag().equals("E")){
            playNote(4);
        }
        if(view.getTag().equals("F")){
            playNote(5);
        }
        if(view.getTag().equals("F#")){
            playNote(6);
        }
        if(view.getTag().equals("G")){
            playNote(7);
        }
        if(view.getTag().equals("Am")){
            playNote(8);
        }
        if(view.getTag().equals("A")){
            playNote(9);
        }
        if(view.getTag().equals("Bm")){
            playNote(10);
        }
        if(view.getTag().equals("B")){
            playNote(11);
        }
    }
    private void playNote(int offset) {
        // Construct a note ON message for the middle C at maximum velocity on channel 1:
        evnt = new byte[3]; //byte array with 3 bytes = evnt
        evnt[0] = (byte) (0x90 | 0x00); // 0x90 = note On, 0x00 = channel 1 (Note on)
        evnt[1] = (byte) (0x3C+offset); // 0x3C = middle C (MIDI Number, note)
        evnt[2] = (byte) 0x7F; // 0x7F = the maximum velocity (127)

        mididr.write(evnt); // Send the MIDI event to the synthesizer.
    }
    private void stopNote() {
        // Construct a note OFF message for the middle C at minimum
        //velocity on channel 1:
        evnt = new byte[3];
        evnt[0] = (byte) (0x80 | 0x00); // 0x80 = note Off, 0x00 = channel 1
        evnt[1] = (byte) 0x3C; // 0x3C = middle C
        evnt[2] = (byte) 0x00; // 0x00 = the minimum velocity (0)
        // Send the MIDI event to the synthesizer.
        mididr.write(evnt);
    }

    @Override
    public void onMidiStart() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}