
public class TLB {
	private int vMemory;
    private int pMemory;
    
    public TLB()
    {
        setVMemory(0);
        setPMemory(0);
        
    }
    
    public void setVMemory(int vMem)
    {
        vMemory = vMem;
    }
    public void setPMemory(int pMem)
    {
        pMemory = pMem;
    }
    public int getVMemory()
    {
        return vMemory;
    }
    public int getPMemory()
    {
        return pMemory;
    }
}
