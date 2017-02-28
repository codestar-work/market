package web;

public class Post {
	long   code;
	String topic;
	String detail;
	long   member;
	String status;
	String updated;
	public long getCode()      { return code;    }
	public String getTopic()   { return topic;   }
	public String getDetail()  { return detail;  }
	public long getMember()    { return member;  }
	public String getStatus()  { return status;  }
	public String getUpdated() { return updated; }
}
