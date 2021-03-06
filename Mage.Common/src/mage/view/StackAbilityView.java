/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Modes;
import mage.abilities.effects.Effect;
import mage.constants.MageObjectType;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbilityView extends CardView {
    private static final long serialVersionUID = 1L;

    private CardView sourceCard;

    public StackAbilityView(Game game, StackAbility ability, String sourceName, CardView sourceCard) {
        this.id = ability.getId();
        this.mageObjectType = MageObjectType.ABILITY_STACK;
        this.sourceCard = sourceCard;
        this.sourceCard.setMageObjectType(mageObjectType);
        this.name = "Ability";
        this.rules = new ArrayList<String>();
        rules.add(ability.getRule(sourceName));
        this.power = ability.getPower().toString();
        this.toughness = ability.getToughness().toString();
        this.loyalty = "";
        this.cardTypes = ability.getCardType();
        this.subTypes = ability.getSubtype();
        this.superTypes = ability.getSupertype();
        this.color = ability.getColor();
        this.manaCost = ability.getManaCost().getSymbols();
        updateTargets(game, ability);
    }

    private void updateTargets(Game game, StackAbility ability) {
        List<String> names = new ArrayList<String>();
        for(UUID modeId : ability.getModes().getSelectedModes()) {
            ability.getModes().setMode(ability.getModes().get(modeId));
            if (ability.getTargets().size() > 0) {
                setTargets(ability.getTargets());
            } else {
                List<UUID> targetList = new ArrayList<UUID>();
                for (Effect effect : ability.getEffects()) {
                    TargetPointer targetPointer = effect.getTargetPointer();
                    if (targetPointer instanceof FixedTarget) {
                        targetList.add(((FixedTarget) targetPointer).getTarget());
                    }
                }
                if (targetList.size() > 0) {
                    overrideTargets(targetList);

                    for (UUID uuid : targetList) {
                        MageObject mageObject = game.getObject(uuid);
                        if (mageObject != null) {
                            names.add(mageObject.getName());
                        }
                    }

                }
            }
        }
        if (!names.isEmpty()) {
            getRules().add("<i>Targets: " + names.toString() + "</i>");
        }
        // show for modal ability, which mode was choosen
        if (ability.isModal()) {
            Modes modes = ability.getModes();
            for(UUID modeId : modes.getSelectedModes()) {
                modes.setMode(modes.get(modeId));
                this.rules.add("<span color='green'><i>Chosen mode: " + ability.getEffects().getText(modes.get(modeId))+"</i></span>");
            }
        }
    }

    public CardView getSourceCard() {
        return this.sourceCard;
    }
}
