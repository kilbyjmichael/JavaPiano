package com.kilby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Piano 
{
	//comps
	private JPanel panel1 = new JPanel();
	
	private JButton[] buttons = new JButton[89];
	private String[] buttonText = new String[89];//{"1", "2", "3", "4", "5", "6", "7", "8"};
	private Color[] keyColor;
	
	private JButton staccato = new JButton("Staccato");
	private JButton legato  = new JButton("Legato");
	
	final MidiChannel[] mc;
	boolean stacOn = false;
	
	public Piano()
	{
		keyColor = new Color[12]; 
			
			keyColor[0] = Color.white;
			keyColor[1] = Color.black;
			keyColor[2] = Color.white;
			keyColor[3] = Color.black;
			keyColor[4] = Color.white;
			
			keyColor[5] = Color.white;
			keyColor[6] = Color.black;
			keyColor[7] = Color.white;
			keyColor[8] = Color.black;
			keyColor[9] = Color.white;
			keyColor[10] = Color.black;
			keyColor[11] = Color.white;
		
		Synthesizer synth = null;
		try
		{
			synth = MidiSystem.getSynthesizer();
		}
		catch (MidiUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			synth.open();
		}
		catch (MidiUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mc = synth.getChannels();
		Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
		synth.loadInstrument(instr[90]);
		
		PianoPlayer play = new PianoPlayer();
		
		
		//starting not music
		panel1.setLayout(new GridLayout(1,88));
		
		int colorLoopCounter = 0;
		for(int x = 1; x < buttons.length; x++)
		{
			if(colorLoopCounter < 12)
			{
				buttonText[x] = Integer.toString(x);
				panel1.add(buttons[x] = new JButton(buttonText[x]));
				buttons[x].setBackground(keyColor[colorLoopCounter]);
				buttons[x].addActionListener(play);
				colorLoopCounter++;
			}
			else
			{
				colorLoopCounter = 0;
				buttonText[x] = Integer.toString(x);
				panel1.add(buttons[x] = new JButton(buttonText[x]));
				buttons[x].setBackground(keyColor[colorLoopCounter]);
				buttons[x].addActionListener(play);
				colorLoopCounter++;
			}
		}
		
		OptionChecker check = new OptionChecker();
		//make options
		staccato.addActionListener(check);
		legato.addActionListener(check);
		

		
		JFrame frame = new JFrame("Piano");
		frame.setLayout(new BorderLayout(5,5));
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(staccato, BorderLayout.EAST);
		frame.add(legato, BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,200);
		frame.setVisible(true);
	}
	
	private class PianoPlayer implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			int note = Integer.parseInt(((JButton) e.getSource()).getText());
			mc[5].noteOn(note,500);
			
			if(stacOn)
			{
				try
				{
					Thread.sleep(300);
					mc[5].noteOff(note);
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class OptionChecker implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == staccato)
			{
				stacOn = true;
			}
			else if(e.getSource() == legato)
			{
				stacOn = false;
			}
		}
	}
	
	public static void main(String[] args) 
	{
		Piano run = new Piano();

	}

}
