package ar.gov.rosario.gait.seg.iface.service;

import java.util.Map;


public interface ISegAuthService {
	
	public boolean auth(String username, String password) throws Exception;
	public boolean auth(String username, String password, Map<String,Object> attributes) throws Exception;

}
