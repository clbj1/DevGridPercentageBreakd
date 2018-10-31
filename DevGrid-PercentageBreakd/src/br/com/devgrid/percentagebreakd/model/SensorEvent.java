package br.com.devgrid.percentagebreakd.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Modelo que representa um evento do sensor de consumo
 * 
 * @author Cesar Buzin
 */
public class SensorEvent implements Comparable<SensorEvent> {

	public final Timestamp createdAt;
	public final List<Integer> apps;

	public SensorEvent(Timestamp createdAt, List<Integer> apps) {
		this.createdAt = createdAt;
		this.apps = apps;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public List<Integer> getApps() {
		return apps;
	}

	@Override
	public int compareTo(SensorEvent arg0) {

		if (this.getCreatedAt() != null && arg0.getCreatedAt() != null)
			return this.getCreatedAt().compareTo(arg0.getCreatedAt());

		return 0;
	}

	@Override
	public String toString() {
		return "SensorEvent [createdAt=" + createdAt + ", apps=" + apps + "]";
	}

}
