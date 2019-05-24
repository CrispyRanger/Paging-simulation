import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class PagingGUI{

	private JFrame frame;
	private JTable vMTbl;
	private JTable tlbTbl;
	private JLabel lblTranslationLookasideBuffer;
	private JTable pTTbl;
	private JTable pMTbl;
	private JLabel lblVirtualMemory;
	private JLabel lblPageTable;
	
	private Thread t;
	
	private TLB[] tlb = new TLB[3];
	private int pMemory[] = new int[8];
	private int vMemory[] = new int[16];
	private  int pfo[] = new int[16];
	private JTable rTbl;
	
	public class PagingThread implements Runnable
	{
		public void run()
		{
			System.out.println("Here run");
			int pCount = 0;
	        int tlbVal = 0;
	        int firstPageEntry = 0;
	        int pfNum[] = new int[pMemory.length];
	        int rCall;
	        boolean isThere;
	        boolean pMemFull = false;
	        
		        
	        //Process loops 20 times for request to virtual memory
	        
	        for(int i = 0; i < 20; i++)
	        {
	            isThere = false;
	            
	            rCall =(int) (Math.random()*16);
	            
	            rTbl.setValueAt(rCall,0,0);
	            
	            rTbl.setBackground(Color.GREEN);
                try
        		{
					t.sleep(1500);
				} 
        		catch (InterruptedException e) 
        		{
					e.printStackTrace();
				}
        		rTbl.setBackground(Color.WHITE);
	            
	            //Loops through the TLB to see if the requested data is present
        		
	            for(int j = 0; j < tlb.length; j++)
	            {
	                //if the data is present set state to true
	                if((tlb[j].getVMemory()) == rCall)
	                {
	                    isThere = true;
	                }
	            }
	            
	            tlbTbl.setBackground(Color.GREEN);
                try
        		{
					t.sleep(1500);
				} 
        		catch (InterruptedException e) 
        		{
					e.printStackTrace();
				}
        		tlbTbl.setBackground(Color.WHITE);
	            
	            //Adds the data to requested area
        		
	            if(!isThere)
	            {
	            	System.out.println(rCall + " " + pCount + " " + firstPageEntry);
	            	
	            	
	            	if(pfo[rCall] == -1)
	            	{
	            		if(pMemFull)
	            		{
	            			pfo[pfNum[firstPageEntry%8]] = -1;
	            			pTTbl.setValueAt(pfo[pfNum[firstPageEntry%8]], pfNum[firstPageEntry%8], 1);
	            			pTTbl.setBackground(Color.GREEN);
		            		try
		            		{
								t.sleep(1500);
							} 
		            		catch (InterruptedException e) 
		            		{
								e.printStackTrace();
							}
		            		pTTbl.setBackground(Color.WHITE);
	            			firstPageEntry++;
	            		}
	            		
	            		pfNum[pCount%8] = rCall;
	            		
	            		pfo[rCall] = pCount%8;
	            		pTTbl.setValueAt(pfo[rCall], rCall, 1);
	            		pTTbl.setBackground(Color.GREEN);
	            		try
	            		{
							t.sleep(1500);
						} 
	            		catch (InterruptedException e) 
	            		{
							e.printStackTrace();
						}
	            		pTTbl.setBackground(Color.WHITE);
	            		
	            		pMemory[pCount%8] = vMemory[rCall];
	            		pMTbl.setValueAt(pMemory[pCount%8], pCount%8, 1);
	            		pMTbl.setBackground(Color.GREEN);
	            		
	            		try
	            		{
							t.sleep(1500);
						} 
	            		catch (InterruptedException e) 
	            		{
							e.printStackTrace();
						}
	            		pMTbl.setBackground(Color.WHITE);

	            		pCount++;
	            		if(pCount==8)
	            			pMemFull = true;	
	            	}
	            	else
	            	{
	            		pTTbl.setBackground(Color.GREEN);
	            		try
	            		{
							t.sleep(1500);
						} 
	            		catch (InterruptedException e) 
	            		{
							e.printStackTrace();
						}
	            		pTTbl.setBackground(Color.WHITE);
	            	}
	            	tlb[tlbVal%3].setVMemory(rCall);
            		tlb[tlbVal%3].setPMemory(pfo[rCall]);
            		
            		tlbTbl.setValueAt((tlb[tlbVal%3].getVMemory()), tlbVal%3,0);
            		tlbTbl.setValueAt((tlb[tlbVal%3].getPMemory()), tlbVal%3,1);
        			
        			tlbTbl.setBackground(Color.GREEN);

            		tlbVal++;
            		
            		try
            		{
						t.sleep(1500);
					} 
            		catch (InterruptedException e) 
            		{
						e.printStackTrace();
					}
            		tlbTbl.setBackground(Color.WHITE);
	            	
	            }
	            else
	            {
	            	System.out.println("It's here");
	            	try
            		{
						t.sleep(1500);
					} 
            		catch (InterruptedException e) 
            		{
						e.printStackTrace();
					}
	            }
	        }
		}
	}
	
	public void readyArr()
	{
		
		for(int i = 0; i<vMemory.length; i++)
		{
			vMemory[i] = (int)(Math.random()*50);
			
			pTTbl.setValueAt(i, i, 0);
			pTTbl.setValueAt(-1, i, 1);
			
			vMTbl.setValueAt(i, i, 0);
			vMTbl.setValueAt(vMemory[i], i, 1);
		}
		
		for(int i = 0; i<pMemory.length; i++)
		{
			pMemory[i] = -1;
			pMTbl.setValueAt(i, i, 0);	
		}
		
		for(int i = 0; i<vMemory.length; i++)
		{
			pfo[i]= (int) pTTbl.getValueAt(i, 1);
			System.out.println(pfo[i]);
		}
		
		for(int i = 0; i<tlb.length;i++)
		{
			tlb[i] = new TLB();
			
			tlb[i].setPMemory((-1));
			tlb[i].setVMemory((-1));
			
			tlbTbl.setValueAt((tlb[i].getPMemory()), i,1);
			tlbTbl.setValueAt((tlb[i].getVMemory()), i,0);
		}
	}

	//Create the application.
	
	public PagingGUI() {
		initialize();
	}

	//Initialize the contents of the frame.

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		vMTbl = new JTable(16,2){
			   public boolean isCellEditable(int row, int column){
			        return false;
			   }
		};
		vMTbl.setRowSelectionAllowed(false);
		vMTbl.setBounds(27, 32, 99, 256);
		frame.getContentPane().add(vMTbl);
		
		
		JLabel lblPhysicalMemory = new JLabel("Physical Memory");
		lblPhysicalMemory.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhysicalMemory.setBounds(299, 11, 99, 14);
		frame.getContentPane().add(lblPhysicalMemory);
		
		tlbTbl = new JTable(3,2){
			   public boolean isCellEditable(int row, int column){
			        return false;
			   }
		};
		tlbTbl.setRowSelectionAllowed(false);
		tlbTbl.setBounds(299, 204, 99, 48);
		frame.getContentPane().add(tlbTbl);
		
		lblTranslationLookasideBuffer = new JLabel("TLB");
		lblTranslationLookasideBuffer.setBounds(334, 184, 37, 14);
		frame.getContentPane().add(lblTranslationLookasideBuffer);
		
		JButton btnPageLocally = new JButton("Locally");
		
		btnPageLocally.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				t.start();
			}
		});
		btnPageLocally.setBounds(299, 324, 99, 48);
		frame.getContentPane().add(btnPageLocally);
		
		pTTbl = new JTable(16,2){
			   public boolean isCellEditable(int row, int column){
			        return false;
			   }
		};
		pTTbl.setRowSelectionAllowed(false);
		pTTbl.setBounds(162, 32, 99, 256);
		frame.getContentPane().add(pTTbl);
		
		pMTbl = new JTable(8,2){
			   public boolean isCellEditable(int row, int column){
			        return false;
			   }
		};
		pMTbl.setRowSelectionAllowed(false);
		pMTbl.setBounds(299, 32, 99, 128);
		frame.getContentPane().add(pMTbl);
		
		lblVirtualMemory = new JLabel("Virtual Memory");
		lblVirtualMemory.setBounds(39, 11, 106, 14);
		frame.getContentPane().add(lblVirtualMemory);
		
		lblPageTable = new JLabel("Page Table");
		lblPageTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageTable.setBounds(162, 11, 99, 14);
		frame.getContentPane().add(lblPageTable);
		
		JLabel lblProcessRequest = new JLabel("Process Request");
		lblProcessRequest.setBounds(37, 327, 115, 14);
		frame.getContentPane().add(lblProcessRequest);
		
		rTbl = new JTable(1,1){
			   public boolean isCellEditable(int row, int column){
			        return false;
			   }
		};
		
		rTbl.setBounds(141, 327, 50, 16);
		frame.getContentPane().add(rTbl);
		
		t = new Thread(new PagingThread());
		
		readyArr();
		System.out.println("Here GUI");
		//t.start();
		System.out.println("Here END");
	}
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PagingGUI window = new PagingGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
