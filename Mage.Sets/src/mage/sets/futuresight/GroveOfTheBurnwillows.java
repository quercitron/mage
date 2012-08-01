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
package mage.sets.futuresight;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jonubuu
 */
public class GroveOfTheBurnwillows extends CardImpl<GroveOfTheBurnwillows> {

    public GroveOfTheBurnwillows(UUID ownerId) {
        super(ownerId, 176, "Grove of the Burnwillows", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "FUT";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {R} or {G} to your mana pool. Each opponent gains 1 life.
        Ability RedManaAbility = new RedManaAbility();
        RedManaAbility.addEffect(new GroveOfTheBurnwillowsEffect());
        this.addAbility(RedManaAbility);
        Ability GreenManaAbility = new GreenManaAbility();
        GreenManaAbility.addEffect(new GroveOfTheBurnwillowsEffect());
        this.addAbility(GreenManaAbility);
    }

    public GroveOfTheBurnwillows(final GroveOfTheBurnwillows card) {
        super(card);
    }

    @Override
    public GroveOfTheBurnwillows copy() {
        return new GroveOfTheBurnwillows(this);
    }
}

class GroveOfTheBurnwillowsEffect extends OneShotEffect<GroveOfTheBurnwillowsEffect> {

    GroveOfTheBurnwillowsEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Each opponent gains 1 life";
    }

    GroveOfTheBurnwillowsEffect(GroveOfTheBurnwillowsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            player.gainLife(1, game);
        }
        return true;
    }

    @Override
    public GroveOfTheBurnwillowsEffect copy() {
        return new GroveOfTheBurnwillowsEffect(this);
    }
}