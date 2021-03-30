package com.Yusuf.bentenmobmod.common.advancement;

import com.Yusuf.bentenmobmod.common.advancement.triggers.FilledVialTrigger;

public class TriggerInit {


	private static FilledVialTrigger register(FilledVialTrigger filledVialTrigger) {
		return filledVialTrigger;
	}

	public static final FilledVialTrigger FILLED_VIAL = register(new FilledVialTrigger());
}

