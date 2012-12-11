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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;

/**
*
* @author LevelX2
*/
public class Overwhelm extends CardImpl<Overwhelm> {

    public Overwhelm(UUID ownerId) {
      super(ownerId, 175, "Overwhelm", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");
      this.expansionSetCode = "RAV";

      // Convoke (Each creature you tap while casting this spell reduces its cost by {1} or by one mana of that creature's color.)
      this.addAbility(new ConvokeAbility());

      // Creatures you control get +3/+3 until end of turn.
      this.getSpellAbility().addEffect(new BoostControlledEffect(3, 3, Duration.EndOfTurn));
    }

    public Overwhelm(final Overwhelm card) {
      super(card);
    }

    @Override
    public Overwhelm copy() {
      return new Overwhelm(this);
    }
}
