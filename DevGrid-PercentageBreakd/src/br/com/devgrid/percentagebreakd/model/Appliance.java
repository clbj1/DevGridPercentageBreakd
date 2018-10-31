package br.com.devgrid.percentagebreakd.model;

/**
 * Modelo que representa uma aplicação
 * 
 * @author Cesar Buzins
 */
public class Appliance {

    public final Integer Id;
    public final String name;
    public final Integer power;

    public Appliance(Integer Id, String name, int power) {
        this.Id = Id;
        this.name = name;
        this.power = power;
    }

	public Integer getId() {
		return Id;
	}

	public String getName() {
		return name;
	}

	public Integer getPower() {
		return power;
	}
}
