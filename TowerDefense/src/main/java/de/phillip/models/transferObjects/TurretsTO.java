package de.phillip.models.transferObjects;

import java.util.List;

public class TurretsTO {
	
	private List<TurretTO> turrets;
	private String turretSpritePath;
	private String cannonSpritePath;
	
	public TurretsTO() {
		
	}
	
	public String getTurretSpritePath() {
		return turretSpritePath;
	}

	public void setTurretSpritePath(String turretSpritePath) {
		this.turretSpritePath = turretSpritePath;
	}

	public String getCannonSpritePath() {
		return cannonSpritePath;
	}

	public void setCannonSpritePath(String cannonSpritePath) {
		this.cannonSpritePath = cannonSpritePath;
	}

	public List<TurretTO> getTurrets() {
		return turrets;
	}

	public void setTurrets(List<TurretTO> turrets) {
		this.turrets = turrets;
	}
}
