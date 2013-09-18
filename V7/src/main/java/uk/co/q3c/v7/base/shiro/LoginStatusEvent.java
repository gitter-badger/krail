package uk.co.q3c.v7.base.shiro;

import org.apache.shiro.subject.Subject;

public class LoginStatusEvent {

	private Subject subject;

	public LoginStatusEvent(Subject subject) {
		super();
		this.subject = subject;
	}

	public Subject getSubject() {
		return subject;
	}
	
	public boolean isSubjectAuthenticated() {
		return getSubject().isAuthenticated();
	}
	
}