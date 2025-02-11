package com.notepad;

//Importing required packages
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//Main class for the Notepad application
public class Notepad {
	JFrame frame;
	JTextArea textArea;
	JMenuBar menuBar;
	JMenu fileMenu, langMenu, formatMenu, commandPromptMenu;
	JMenuItem itemNew, itemOpen, itemNewWindow, itemSaveAs, itemSave, itemExit;
	JMenuItem itemJava, itemPython, itemJavascript, itemPHP, itemMySQL, itemHTML, itemCSS, itemC, itemCPP, itemCSharp,
			itemRuby, itemGoLang, itemSwift, itemKotlin;
	JMenuItem itemWordWrap, itemFont, itemFontSize;
	JMenuItem itemOpenCMD;

	String openPath = null;
	String openFilename = null;

	boolean wrap = false;

	Font fontArial, fontTimesNewRoman, fontConsolas, fontRegular;
	String fontStyle = "Arial";

	public Notepad() {
		super();
		createFrame();
		createTextArea();
		createScrollBars();
		createMenuBar();
		createFileMenuItems();
		createLangMenuItems();
		createFormatMenuItems();
		createCommandPromptMenuItems();
	}

	// Creating the main frame
	public void createFrame() {
		frame = new JFrame("Notepad");
		frame.setSize(700, 350);
		Image icon = Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\Sachin Mogaronkar\\Development_Internship\\Notepad\\src\\com\\notepad\\notebook_icon.png");
		frame.setIconImage(icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// Creating the text area
	public void createTextArea() {
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		frame.add(textArea);

	}

	// Adding scroll functionality to the text area
	public void createScrollBars() {
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scroll);
	}

	// Creating the menu bar
	public void createMenuBar() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		langMenu = new JMenu("Language");
		menuBar.add(langMenu);
		formatMenu = new JMenu("Format");
		menuBar.add(formatMenu);
		commandPromptMenu = new JMenu("Command Prompt");
		menuBar.add(commandPromptMenu);
	}

	// File menu creation
	public void createFileMenuItems() {

		// Adding New as sub menu option.
		itemNew = new JMenuItem("New");
		itemNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				frame.setTitle("Untitled Notepad");

				openFilename = null;
				openPath = null;
			}
		});
		fileMenu.add(itemNew);

		// Adding New Window as sub menu option.
		itemNewWindow = new JMenuItem("New Window");
		itemNewWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Notepad np1 = new Notepad();
				np1.frame.setTitle("Untitled");
			}
		});
		fileMenu.add(itemNewWindow);

		// Adding Open as sub menu option.
		itemOpen = new JMenuItem("Open");
		itemOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);
				fd.setVisible(true);
				String filename = fd.getFile();
				String path = fd.getDirectory();
				if (filename != null) {
					frame.setTitle(filename);
					openFilename = filename;
					openPath = path;
				}
//				System.out.println(path+filename);
				BufferedReader bfReader = null;
				try {
					bfReader = new BufferedReader(new FileReader(path + filename));
					try {
						String sentence = bfReader.readLine();
						textArea.setText("");
						while (sentence != null) {
							textArea.append(sentence + "\n");
							sentence = bfReader.readLine();
						}
					} catch (FileNotFoundException e1) {
						System.out.println("File not found.");
					}
				} catch (IOException e1) {
					System.out.println("Data could not be read.");
				} catch (NullPointerException e2) {

				} finally {
					try {
						bfReader.close();
					} catch (IOException e1) {
						System.out.println("File could not be closed.");
					} catch (NullPointerException e2) {

					}
				}
			}
		});
		fileMenu.add(itemOpen);

		// Adding Save as sub menu option.
		itemSave = new JMenuItem("Save");
		itemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (openFilename != null && openPath != null) {
					writeDataToFile(openFilename, openPath);
				} else {
					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
					fd.setVisible(true);
					String path = fd.getDirectory();
					String filename = fd.getFile();
					BufferedWriter bfWriter = null;
					if (filename != null && path != null) {
						try {
							bfWriter = new BufferedWriter(new FileWriter(path + filename));
							String text = textArea.getText();
							bfWriter.write(text);
						} catch (IOException e1) {
							System.out.println("Data cannot be written.");
						} finally {
							try {
								bfWriter.close();
							} catch (IOException e1) {
								System.out.println("File cannot be closed.");
							} catch (NullPointerException e2) {
								System.out.println("File not found to closed.");
							}
						}
					}
				}
			}
		});
		fileMenu.add(itemSave);

		// Adding Save as as sub menu option.
		itemSaveAs = new JMenuItem("Save As");
		itemSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (openFilename != null && openPath != null) {
					writeDataToFile(openFilename, openPath);
				} else {
					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
					fd.setVisible(true);
					String path = fd.getDirectory();
					String filename = fd.getFile();
					if (filename != null && path != null) {
						writeDataToFile(filename, path);
						openFilename = filename;
						openPath = path;
					}
				}
			}
		});

		fileMenu.add(itemSaveAs);

		// Adding Exit as sub menu option.
		itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		fileMenu.add(itemExit);
	}

	// Language menu creation
	public void createLangMenuItems() {
		// Adding Java as sub menu option.
		itemJava = new JMenuItem("Java");
		itemJava.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("java.txt");
			}
		});
		langMenu.add(itemJava);
		// Adding Python as sub menu option.
		itemPython = new JMenuItem("Python");
		itemPython.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("python.txt");
			}
		});
		langMenu.add(itemPython);
		// Adding Javascript as sub menu option.
		itemJavascript = new JMenuItem("Javascript");
		itemJavascript.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("javascript.txt");
			}
		});
		langMenu.add(itemJavascript);
		// Adding PHP as sub menu option.
		itemPHP = new JMenuItem("PHP");
		itemPHP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("php.txt");
			}
		});
		langMenu.add(itemPHP);
		// Adding MySQL as sub menu option.
		itemMySQL = new JMenuItem("My SQL");
		itemMySQL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("mysql.txt");
			}
		});
		langMenu.add(itemMySQL);
		// Adding HTML as sub menu option.
		itemHTML = new JMenuItem("HTML");
		itemHTML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("html.txt");
			}
		});
		langMenu.add(itemHTML);
		// Adding CSS as sub menu option.
		itemCSS = new JMenuItem("CSS");
		itemCSS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("css.txt");
			}
		});
		langMenu.add(itemCSS);
		// Adding C as sub menu option.
		itemC = new JMenuItem("C");
		itemC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("c.txt");
				
			}
		});
		langMenu.add(itemC);
		// Adding C++ as sub menu option.
		itemCPP = new JMenuItem("C++");
		itemCPP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("c++.txt");
			}
		});
		langMenu.add(itemCPP);
		// Adding C# as sub menu option.
		itemCSharp = new JMenuItem("C#");
		itemCSharp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("c#.txt");
			}
		});
		langMenu.add(itemCSharp);
		// Adding Ruby as sub menu option.
		itemRuby = new JMenuItem("Ruby");
		itemRuby.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("ruby.txt");
			}
		});
		langMenu.add(itemRuby);
		// Adding GoLang as sub menu option.
		itemGoLang = new JMenuItem("Go Lang");
		itemGoLang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("golang.txt");
			}
		});
		langMenu.add(itemGoLang);
		// Adding Swift as sub menu option.
		itemSwift = new JMenuItem("Swift");
		itemSwift.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("swift.txt");
			}
		});
		langMenu.add(itemSwift);
		// Adding Kotlin as sub menu option.
		itemKotlin = new JMenuItem("Kotlin");
		itemKotlin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("kotlin.txt");
			}
		});
		langMenu.add(itemKotlin);
	}

	// Methods Language selection functionality
	public void setLanguage(String filename) {
		String path="C:\\Users\\Sachin Mogaronkar\\Development_Internship\\Notepad\\LangBoilerPlate\\";
		BufferedReader bfReader = null;
		try {
			bfReader = new BufferedReader(new FileReader(path + filename));
			try {
				String sentence = bfReader.readLine();
				textArea.setText("");
				while (sentence != null) {
					textArea.append(sentence + "\n");
					sentence = bfReader.readLine();
				}
			} catch (FileNotFoundException e1) {
				System.out.println("File not found.");
			}
		} catch (IOException e1) {
			System.out.println("Data could not be read.");
		} catch (NullPointerException e2) {

		} finally {
			try {
				bfReader.close();
			} catch (IOException e1) {
				System.out.println("File could not be closed.");
			} catch (NullPointerException e2) {

			}
		}
	}

	// Format menu creation
	public void createFormatMenuItems() {
		// Adding Word Wrap as sub menu option.
		itemWordWrap = new JMenuItem("Word wrap: Off");
		itemWordWrap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (wrap == false) {
					textArea.setLineWrap(true);
					textArea.setWrapStyleWord(true);
					wrap = true;
					itemWordWrap.setText("Word wrap: On");
				} else {
					textArea.setLineWrap(false);
					textArea.setWrapStyleWord(false);
					wrap = false;
					itemWordWrap.setText("Word wrap: Off");
				}
			}
		});
		formatMenu.add(itemWordWrap);

		// Adding Select Font as sub menu option.
		itemFont = new JMenu("Select Font");

		// Adding Arial Font as font-sub menu option.
		JMenuItem itemArial = new JMenuItem("Arial");
		itemArial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Arial");
			}
		});
		itemFont.add(itemArial);

		// Adding Times new Roman Font as font-sub menu option.
		JMenuItem itemTNR = new JMenuItem("Times new Roman");
		itemTNR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Times new Roman");
			}
		});
		itemFont.add(itemTNR);

		// Adding Consolas Font as font-sub menu option.
		JMenuItem itemConsolas = new JMenuItem("Consolas");
		itemConsolas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Consolas");
			}
		});
		itemFont.add(itemConsolas);

		// Adding Regular Font as font-sub menu option.
		JMenuItem itemRegular = new JMenuItem("Regular");
		itemRegular.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Regular");
			}
		});
		itemFont.add(itemRegular);

		// Adding Georgia Font as font-sub menu option.
		JMenuItem itemGeorgia = new JMenuItem("Georgia");
		itemGeorgia.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Georgia");
			}
		});
		itemFont.add(itemGeorgia);

		// Adding Garamond Font as font-sub menu option.
		JMenuItem itemGaramond = new JMenuItem("Garamond");
		itemGaramond.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Garamond");
			}
		});
		itemFont.add(itemGaramond);

		// Adding Helvetica Font as font-sub menu option.
		JMenuItem itemHelvetica = new JMenuItem("Helvetica");
		itemHelvetica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Helvetica");
			}
		});
		itemFont.add(itemHelvetica);

		// Adding Verdana Font as font-sub menu option.
		JMenuItem itemVerdana = new JMenuItem("Verdana");
		itemVerdana.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Verdana");
			}
		});
		itemFont.add(itemVerdana);

		// Adding CourierNew Font as font-sub menu option.
		JMenuItem itemCourierNew = new JMenuItem("CourierNew");
		itemCourierNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("CourierNew");
			}
		});
		itemFont.add(itemCourierNew);

		formatMenu.add(itemFont);

		// Adding Select Font size as sub menu option.
		itemFontSize = new JMenu("Select Font Size");
		JMenuItem size10 = new JMenuItem("10");
		size10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(10);
			}
		});
		itemFontSize.add(size10);
		JMenuItem size12 = new JMenuItem("12");
		size12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(12);
			}
		});
		itemFontSize.add(size12);
		JMenuItem size14 = new JMenuItem("14");
		size14.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(14);
			}
		});
		itemFontSize.add(size14);
		JMenuItem size16 = new JMenuItem("16");
		size16.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(16);
			}
		});
		itemFontSize.add(size16);
		JMenuItem size18 = new JMenuItem("18");
		size18.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(18);
			}
		});
		itemFontSize.add(size18);
		JMenuItem size20 = new JMenuItem("20");
		size20.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(20);
			}
		});
		itemFontSize.add(size20);
		JMenuItem size22 = new JMenuItem("22");
		size22.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(22);
			}
		});
	}

	// Methods for setting the Font size
	public void setFontSize(int size) {
		fontArial = new Font("Arial", Font.PLAIN, size);
		fontTimesNewRoman = new Font("Times new Roman", Font.PLAIN, size);
		fontConsolas = new Font("Consolas", Font.PLAIN, size);
		fontRegular = new Font("Regular", Font.PLAIN, size);
		setFont(fontStyle);
	}

	// Methods for setting the Font style
	public void setFont(String fontName) {
		fontStyle = fontName;
		switch (fontName) {
		case "Arial": {
			textArea.setFont(fontArial);
			break;
		}
		case "Times new Roman": {
			textArea.setFont(fontTimesNewRoman);
			break;
		}
		case "Consolas": {
			textArea.setFont(fontConsolas);
			break;
		}
		case "Regular": {
			textArea.setFont(fontRegular);
			break;
		}
		default: {
			break;
		}
		}
	}

	// Integrating the command prompt
	public void createCommandPromptMenuItems() {
		itemOpenCMD = new JMenuItem("Open CMD");
		itemOpenCMD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (openPath != null) {
						Runtime.getRuntime().exec(new String[] { "cmd", "/K", "start" }, null, new File(openPath));
					} else {
						Runtime.getRuntime().exec(new String[] { "cmd", "/K", "start" }, null, null);
					}
				} catch (IOException e1) {
					System.out.println("Could not launch CMD.");
				}

			}
		});
		commandPromptMenu.add(itemOpenCMD);
	}

	// Method to write the data within the file.
	public void writeDataToFile(String filename, String path) {
		BufferedWriter bfWriter = null;
		if (filename != null && path != null) {
			try {
				bfWriter = new BufferedWriter(new FileWriter(path + filename));
				String text = textArea.getText();
				bfWriter.write(text);
			} catch (IOException e1) {
				System.out.println("Data cannot be written.");
			} finally {
				try {
					bfWriter.close();
				} catch (IOException e1) {
					System.out.println("File cannot be closed.");
				} catch (NullPointerException e2) {
					System.out.println("File not found to closed.");
				}
			}
		}
	}

}
