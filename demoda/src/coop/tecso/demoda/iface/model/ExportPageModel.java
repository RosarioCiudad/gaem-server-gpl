
package coop.tecso.demoda.iface.model;



public class ExportPageModel extends PageModel {

	private byte[] File = {};
	private String fileOutPutName = "";

	// Getters y setters ******************************************************************************************


	public String getFileOutPutName() {
		return fileOutPutName;
	}
	public void setFileOutPutName(String fileOutPutName) {
		this.fileOutPutName = fileOutPutName;
	}


	public byte[] getFile() {
		return File;
	}
	public void setFile(byte[] file) {
		File = file;
	}
}
