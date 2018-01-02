package autoloader.conf;

import java.util.Map;

public class Website {

	private String url;
	private Map<String, Reference> contentMap;

	public Website() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Reference> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<String, Reference> contentMap) {
		this.contentMap = contentMap;
	}

}
