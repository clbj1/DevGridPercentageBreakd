package br.com.devgrid.percentagebreakd.exec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.devgrid.percentagebreakd.exception.InvalidDataException;
import br.com.devgrid.percentagebreakd.model.Appliance;
import br.com.devgrid.percentagebreakd.model.SensorEvent;
import br.com.devgrid.percentagebreakd.util.DateTimeUtil;

/**
 * Classe responsável por executar cálculos de consumo por aplicação
 * 
 * @author Cesar Buzin
 */
public class BreakdownCalculatorExec {

	/**
	 * Método principal utilizado para calcular o percentual de consumo de energia
	 * por hora da aplicação
	 * 
	 * @param sensorEvents
	 * @param appliance
	 * @return
	 * @throws InvalidDataException
	 */
	public List<Integer> calculate(List<SensorEvent> sensorEvents, Appliance appliance) throws InvalidDataException {

		// Se não foram retornados dados do sensor, significa que não foi utilizado
		if (sensorEvents == null || sensorEvents.isEmpty())
			return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

		// Primeiramente, garante que listagem retornou em ordem crescente de horário
		Collections.sort(sensorEvents);

		// Map de chave HORA, que contém map de chave FREQUENCIA com a quantidade de
		// minutos da respective frequência/hora
		HourControll hourControl = new HourControll();

		for (SensorEvent sensorEvent : sensorEvents) {

			// Valida se o ID da applicação foi informado
			if (appliance.getId().compareTo(sensorEvent.getApps().size()) > 0)
				continue;

			// Busca hora do timestamp analisado
			Integer hour = DateTimeUtil.getHour(sensorEvent.getCreatedAt());

			// Busca power do evento
			Integer power = sensorEvent.getApps().get(appliance.getId() - 1);
			
			// Ignora caso consumo não tenha sido informado
			if(power == null)
				continue;

			// Processa informação do sensor
			processPowerHour(appliance, hourControl, hour, power);
		}

		// Guarda percentual de consumo da última hora, uma vez que todos enventos já
		// foram informados
		processPowerHour(appliance, hourControl, hourControl.getLastHour() + 1, 0);

		// Guarda demais horas do dia para montagem da lista final
		for (int lastHour = hourControl.getLastHour(); lastHour < 24; lastHour++) {
			hourControl.getMapBiggestPerHour().put(lastHour, 0);
		}

		// Prepara lista para retorno baseado nos values do MAP
		List<Integer> lstReturn = new ArrayList<Integer>();
		lstReturn.addAll(hourControl.getMapBiggestPerHour().values());

		return lstReturn;
	}

	/**
	 * Processa consumo informado para a hora
	 * 
	 * @param appliance
	 * @param hourControl
	 * @param hour
	 * @param power
	 * @throws InvalidDataException
	 */
	private void processPowerHour(Appliance appliance, HourControll hourControl, Integer hour, Integer power)
			throws InvalidDataException {

		if (power.compareTo(appliance.getPower()) > 0)
			throw new InvalidDataException("Invalid data: power used is bigger then appliance power allowed");

		// Se alterou a hora, adiciona percentual de consumo no map e variáveis
		// auxiliares
		// Caso contrário, apenas guarda maior consumo informado para hora
		if (hour > hourControl.getLastHour()) {

			// Caso o sensor tenha pulado mais de uma hora, adiciona percentuais zerados de
			// consumo ao mapa
			for (int i = hourControl.getLastHour() + 1; i < hour; i++) {
				hourControl.getMapBiggestPerHour().put(i, 0);
			}

			hourControl.getMapBiggestPerHour().put(hourControl.getLastHour(), calculatePercentualHour(
					hourControl.getBiggestPower(), hourControl.getLastPowerLastHour(), appliance.getPower()));
			hourControl.setLastPowerLastHour(hourControl.getBiggestPower());
			hourControl.setLastHour(hour);
		} else if (power > hourControl.getBiggestPower()) {
			hourControl.setBiggestPower(power);
		}
	}

	/**
	 * Calcula percentual de energia utilizado na hora em relação ao total permitido
	 * pela aplicação
	 * 
	 * @param biggestPower
	 * @param lastPowerLastHour
	 * @param powerAppliance
	 * @return
	 */
	private Integer calculatePercentualHour(Integer biggestPower, Integer lastPowerLastHour, Integer powerAppliance) {

		return ((biggestPower - lastPowerLastHour) * 100) / powerAppliance;
	}

	/**
	 * Classe auxiliar para verificação de consumo por hora
	 * 
	 * @author Cesar Buzin
	 */
	private class HourControll {

		private Map<Integer, Integer> mapBiggestPerHour;
		private Integer lastPowerLastHour;
		private Integer lastHour;
		private Integer biggestPower;

		public HourControll() {
			super();
			this.mapBiggestPerHour = new HashMap<Integer, Integer>();
			this.lastPowerLastHour = 0;
			this.lastHour = 0;
			this.biggestPower = 0;
		}

		public Map<Integer, Integer> getMapBiggestPerHour() {
			return mapBiggestPerHour;
		}

		public Integer getLastPowerLastHour() {
			return lastPowerLastHour;
		}

		public void setLastPowerLastHour(Integer lastPowerLastHour) {
			this.lastPowerLastHour = lastPowerLastHour;
		}

		public Integer getLastHour() {
			return lastHour;
		}

		public void setLastHour(Integer lastHour) {
			this.lastHour = lastHour;
		}

		public Integer getBiggestPower() {
			return biggestPower;
		}

		public void setBiggestPower(Integer biggestPower) {
			this.biggestPower = biggestPower;
		}
	}
}
