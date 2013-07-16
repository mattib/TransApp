package com.transapp.extentions.models;

import java.util.Date;
import java.util.Random;

public class TaskItem {

	//TaskItem properties
	private int m_id;
	private String m_deliveryNumber;
	private Integer m_userId;
	private Integer m_companyId;
	private String m_senderAddress;
	private String m_reciverAddress;
	private TaskStatus m_taskStatus;
	private Date m_pickedUpAt;
	private Date m_deliveredAt;
	private Date m_pickUpTime;
	private Date m_deliveryTime;
	private Date m_lastModified;
	private String m_comment;
	private java.lang.Integer m_contactId;
	private java.lang.Integer m_taskType;
	private String m_dataExtention;
	private String m_signatureId;
	private String m_imageId;
	private String m_userComment;
	private Boolean m_rejected;

	// Empty constructor
	public TaskItem(){
	}
	
	// constructor with Id
		public TaskItem(int id, String deliveryNumber, int userId, int companyId, String senderAddress, String reciverAddress,
				TaskStatus taskStatus, Date pickedUpAt, Date deliveredAt, Date pickUpTime, Date deliveryTime, Date lastModified,
				String comment, java.lang.Integer contactId, java.lang.Integer taskType, String dataExtention,
				String signatureId, String imageId, String userComment, Boolean rejected) {
			this(deliveryNumber, userId, companyId, senderAddress, reciverAddress, taskStatus, pickedUpAt,
					deliveredAt, pickUpTime, deliveryTime, lastModified, comment, contactId, taskType, dataExtention,
					signatureId, imageId, userComment, rejected);
			m_id = id;
		}
		
	// constructor without Id
	public TaskItem(String deliveryNumber, int userId, int companyId, String senderAddress, String reciverAddress,
			TaskStatus taskStatus, Date pickedUpAt, Date deliveredAt, Date pickUpTime, Date deliveryTime, Date lastModified,
			String comment, java.lang.Integer contactId, java.lang.Integer taskType, String dataExtention,
			String signatureId, String imageId, String userComment, Boolean rejected) {
		m_deliveryNumber = deliveryNumber;
		m_userId = userId;
		m_companyId = companyId;
		m_senderAddress = senderAddress;
		m_reciverAddress = reciverAddress;
		m_taskStatus = taskStatus;
		m_pickedUpAt = pickedUpAt;
		m_deliveredAt = deliveredAt;
		m_pickUpTime = pickUpTime;
		m_deliveryTime = deliveryTime;
		m_lastModified = lastModified;
		m_comment = comment;
		m_contactId = contactId;
		m_taskType = taskType;
		m_dataExtention = dataExtention;
		m_signatureId = signatureId;
		m_imageId = imageId;
		m_userComment = userComment;
		m_rejected = rejected;
	}

	public int GetId()
	{ return m_id; }
	
	public void SetId(int value)
	{ m_id = value; }
	
	public String GetDeliveryNumber()
	{ return m_deliveryNumber; }
	
	public void SetDeliveryNumber(String value)
	{ m_deliveryNumber = value; }
	
	public java.lang.Integer GetUserId()
	{ return m_userId; }
	
	public void SetUserId(int value)
	{ m_userId = value; }
	
	public java.lang.Integer GetCompanyId()
	{ return m_companyId; }
	
	public void SetCompanyId(int value)
	{ m_companyId = value; }
	
	public String GetSenderAddress()
	{ return m_senderAddress; }
	
	public void SetSenderAddress(String value)
	{ m_senderAddress = value; }
	
	public String GetReciverAddress()
	{ return m_reciverAddress; }
	
	public void SetReciverAddress(String value)
	{ m_reciverAddress = value; }
	
	public TaskStatus GetTaskStatus()
	{ return m_taskStatus; }
	
	public void SetTaskStatus(TaskStatus value)
	{ m_taskStatus = value; }
	
	public Date GetPickedUpAt()
	{ return m_pickedUpAt; }
	
	public void SetPickedUpAt(Date value)
	{ m_pickedUpAt = value; }
	
	public Date GetDeliveredAt()
	{ return m_deliveredAt; }
	
	public void SetDeliveredAt(Date value)
	{ m_deliveredAt = value; }
	
	public Date GetPickUpTime()
	{ return m_pickUpTime; }
	
	public void SetPickUpTime(Date value)
	{ m_pickUpTime = value; }
	
	public Date GetDeliveryTime()
	{ return m_deliveryTime; }
	
	public void SetDeliveryTime(Date value)
	{ m_deliveryTime = value; }
	
	public Date GetLastModified()
	{ return m_lastModified; }
	
	public void SetLastModified(Date value)
	{ m_lastModified = value; }
	
	public String GetComment()
	{ return m_comment; }
	
	public void SetComment(String value)
	{ m_comment = value; }
	
	public java.lang.Integer GetContactId()
	{ return m_contactId; }
	
	public void SetContactId(int value)
	{ m_contactId = value; }
	
	public java.lang.Integer GetTaskType()
	{ return m_taskType; }
	
	public void SetTaskType(int value)
	{ m_taskType = value; }
	
	public String GetDataExtention()
	{ return m_dataExtention; }
	
	public void SetDataExtention(String value)
	{ m_dataExtention = value; }
	
	public String GetSignatureId()
	{ return m_signatureId; }
	
	public void SetSignatureId(String value)
	{ m_signatureId = value; }
	
	public String GetImageId()
	{ return m_imageId; }
	
	public void SetImageId(String value)
	{ m_imageId = value; }
	
	public String GetUserComment()
	{ return m_userComment; }
	
	public void SetUserComment(String value)
	{ m_userComment = value; }
	
	public Boolean GetRejected()
	{ return m_rejected; }
	
	public void SetRejected(Boolean value)
	{ m_rejected = value; }
}
