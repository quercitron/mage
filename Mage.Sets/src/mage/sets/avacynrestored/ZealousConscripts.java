/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.avacynrestored;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public class ZealousConscripts extends CardImpl<ZealousConscripts> {

    public ZealousConscripts(UUID ownerId) {
        super(ownerId, 166, "Zealous Conscripts", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
        // When Zealous Conscripts enters the battlefield, gain control of target permanent until end of turn. Untap that permanent. It gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Constants.Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent());
        ability.addEffect(new UntapTargetEffect());
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Constants.Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public ZealousConscripts(final ZealousConscripts card) {
        super(card);
    }

    @Override
    public ZealousConscripts copy() {
        return new ZealousConscripts(this);
    }
}