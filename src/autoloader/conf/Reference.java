package autoloader.conf;

public class Reference {

	private String name;
	private String url;
	private String activity;

	public Reference() {
		super();
	}

	public Reference(String name, String url) {
		super();
		this.name = name;
		this.url = url;
		this.activity = Boolean.toString(false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String active) {
		this.activity = active;
	}

	public boolean isActive() {
		return activity.toLowerCase().equals("true");
	}

	public void setActive(boolean active) {
		this.activity = String.valueOf(active);
	}

}
