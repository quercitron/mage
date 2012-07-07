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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author North
 */
public class Mutilate extends CardImpl<Mutilate> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp you control");

    static {
        filter.getSubtype().add("Swamp");
        filter.setTargetController(Constants.TargetController.YOU);
    }

    public Mutilate(UUID ownerId) {
        super(ownerId, 102, "Mutilate", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "M13";

        this.color.setBlack(true);

        // All creatures get -1/-1 until end of turn for each Swamp you control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter, -1);
        this.getSpellAbility().addEffect(new BoostAllEffect(count, count, Duration.EndOfTurn));
    }

    public Mutilate(final Mutilate card) {
        super(card);
    }

    @Override
    public Mutilate copy() {
        return new Mutilate(this);
    }
}