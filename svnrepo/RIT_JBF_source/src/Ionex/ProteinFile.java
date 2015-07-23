package Ionex;

public class ProteinFile
{
    String	m_strName;
    String	m_strFile;

    public ProteinFile(String strName, String strFile)
    {
        m_strName = strName;
        m_strFile = strFile;
    }

    public String getName()
    {
        return m_strName;
    }

    public String getFile()
    {
        return m_strFile;
    }
}

