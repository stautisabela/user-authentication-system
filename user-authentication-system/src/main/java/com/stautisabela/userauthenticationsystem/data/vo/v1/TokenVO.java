package com.stautisabela.userauthenticationsystem.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private Boolean authenticated;
	private Date creationDate;
	private Date expirationDate;
	private String accessToken;
	private String refreshToken;
	
	public TokenVO() {
		
	}

	public TokenVO(String username, Boolean authenticated, Date creationDate, Date expirationDate, String accessToken,
			String refreshToken) {
		this.username = username;
		this.authenticated = authenticated;
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, authenticated, creationDate, expirationDate, refreshToken, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenVO other = (TokenVO) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(authenticated, other.authenticated)
				&& Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(expirationDate, other.expirationDate)
				&& Objects.equals(refreshToken, other.refreshToken) && Objects.equals(username, other.username);
	}
}
