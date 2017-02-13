package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class Bomb extends AbstractAbility {

	/**
	 * Attributes of the ability 'Bomb'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.BOMB);

	public Bomb() {
		setStats(abilityDataClass);
	}
}
