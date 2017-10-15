package fileExplorerService;

import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args){
		
		
		String path = null;
		
		if(args.length == 1){
			path = args[0];			
		}
		
		path = "D:\\Windows 8";
		
		if(path != null){
			
			File folder = getFolder(path);
			
			File[] listOfFiles = folder.listFiles();
			
			FileObj[] fileArray = getFileObjs(listOfFiles);
			
	//			System.out.println("unsorted: ");
	//			for(int i = 0; i < fileArray.length; i++){
	//				System.out.println("File Name: " + fileArray[i].fileName + " File Size: " + fileArray[i].fileSize);
	//			}
	
			QuickSort qs = new QuickSort();
			
			System.out.println("sorted by filesize: ");
			FileObj[] sortedFileArray = qs.sortFileObj(fileArray);
			
			for(int i = 0; i < sortedFileArray.length; i++){
				System.out.println("File Name: " + sortedFileArray[i].fileName + " File Size: " + sortedFileArray[i].fileSize + "mbs");
			}
			
		}
		else{
			System.out.println("Usage: Put in file path for recursive search for the largest file in the path");
			return;
		}
		

		
	}
	
	private static File getFolder(String folderPath){
		
		File folder = new File(folderPath);
		
		return folder;
	}
	
	private static FileObj[] getFileObjs(File[] files){
		ArrayList<FileObj> fileArrayList = new ArrayList<FileObj>();
		
		for ( int i = 0; i < files.length; i ++){
			if(files[i].isFile()){
				long fileSize = files[i].length();

				fileArrayList.add(new FileObj(files[i].getName(), getFileSizeInMB(fileSize)));
			}
			else if(files[i].isDirectory()){
				String filePath = files[i].getPath();
				File folder = getFolder(filePath);
				
				File[] listOfFiles = folder.listFiles();
				
				FileObj[] tempFileAray = getFileObjs(listOfFiles);

				for( int j = 0; j < tempFileAray.length; j++){
					fileArrayList.add(tempFileAray[j]);
				}
				
			}
			
		}
		
		FileObj[] fileArray = new FileObj[fileArrayList.size()];
		
		
		for( int i = 0; i < fileArrayList.size(); i++){
			fileArray[i] = fileArrayList.get(i);
		}
		
		return fileArray;
	}
	
	private static float getFileSizeInMB(long fileSizeInBytes){
		long kbytes = 1024;
		
		// Get length of file in bytes
		float fileSizeInKB = fileSizeInBytes / kbytes;
		float fileSizeInMB = fileSizeInKB / kbytes;
		
		return fileSizeInMB;
	}
	
}