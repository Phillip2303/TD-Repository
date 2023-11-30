package de.phillip.models.transferObjects;

import java.util.List;

public class WaveTO {
	
	private List <WaveBlockTO> waveBlocks;
	private int waveCount;

	public List<WaveBlockTO> getWaveBlocks() {
		return waveBlocks;
	}

	public void setWaveBlocks(List<WaveBlockTO> waveBlocks) {
		this.waveBlocks = waveBlocks;
	}

	public int getWaveCount() {
		return waveCount;
	}

	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
	}

	public WaveTO() {

	}

}
