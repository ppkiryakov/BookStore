package bg.pragmatic.bookstore.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bg.pragmatic.bookstore.model.exceptions.LoadingFailedException;

public class Music extends Product {

	private int yearOfPublish;
	private int songsCount;
	
	public Music() {
		super(ProductType.MUSIC);
	}
		
	public void setYearOfPublish(int yearOfPublish) {
		this.yearOfPublish = yearOfPublish;
	}
	
	public int getYearOfPublish() {
		return yearOfPublish;
	}
	
	public void setSongsCount(int songsCount) {
		this.songsCount = songsCount;
	}
	
	public int getSongsCount() {
		return songsCount;
	}

	@Override
	protected List<String> convertAdditionalProperties() {
		List<String> values = new ArrayList<>();		
		values.add(String.valueOf(yearOfPublish));
		values.add(String.valueOf(songsCount));
		
		return values;
	}

	@Override
	protected void loadAdditionalProperties(List<String> values) throws LoadingFailedException {
		final int propertiesCount = 2;
		if(values.size() < propertiesCount) {
			throw new LoadingFailedException("Insufficient values");
		}
		
		Iterator<String> iterator = values.iterator();
		yearOfPublish = Integer.valueOf(iterator.next());
		songsCount = Integer.valueOf(iterator.next());
	}

}
