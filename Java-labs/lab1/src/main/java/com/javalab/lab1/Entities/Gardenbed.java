package com.javalab.lab1.Entities;

public class Gardenbed 
{
    private Long id;
    private String number;
    private Long userId;
    private String size;
    private Boolean status;

    public Gardenbed(){}

    public Gardenbed(Long id, String number, Long userId, String size, Boolean status)
    {
        this.id = id;
        this.number = number;
        this.userId = userId;
        this.size = size;
        this.status = status;
    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getnumber()
    {
        return number;
    }
    public void setnumber(String number)
    {
        this.number = number;
    }
    public Long getuserId()
    {
        return userId;
    }
    public void setuserId(Long userId)
    {
        this.userId = userId;
    }
    public String getsize()
    {
        return size;
    }
    public void setsize(String size)
    {
        this.size = size;
    }
    public boolean Getstatus()
    {
        return status;
    }
    public void Setstatus(boolean status)
    {
        this.status = status;
    }
}
