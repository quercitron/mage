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
package mage.sets.worldwake;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class CometStorm extends CardImpl<CometStorm> {

    public CometStorm(UUID ownerId) {
        super(ownerId, 76, "Comet Storm", Rarity.MYTHIC, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");
        this.expansionSetCode = "WWK";

        this.color.setRed(true);

        // Multikicker {1}
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new EmptyEffect("Multikicker {1}")));

        // Choose target creature or player, then choose another target creature or player for each time Comet Storm was kicked. Comet Storm deals X damage to each of them.
        this.getSpellAbility().addEffect(new CometStormEffect());
    }

    public CometStorm(final CometStorm card) {
        super(card);
    }

    @Override
    public CometStorm copy() {
        return new CometStorm(this);
    }
}

class CometStormEffect extends OneShotEffect<CometStormEffect> {

    public CometStormEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Choose target creature or player, then choose another target creature or player for each time Comet Storm was kicked. Comet Storm deals X damage to each of them";
    }

    public CometStormEffect(final CometStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX() + 1;
        int damage = source.getManaCostsToPay().getX();
        Player you = game.getPlayer(source.getControllerId());
        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer(amount);
        if (you != null) {
            if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Damage, source.getControllerId(), source.getId(), game)) {
                if (!target.getTargets().isEmpty()) {
                    List<UUID> targets = target.getTargets();
                    for (UUID uuid : targets) {
                        Permanent permanent = game.getPermanent(uuid);
                        Player player = game.getPlayer(uuid);
                        if (permanent != null) {
                            permanent.damage(damage, source.getId(), game, true, false);
                        }
                        if (player != null) {
                            player.damage(damage, source.getId(), game, true, false);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CometStormEffect copy() {
        return new CometStormEffect(this);
    }
}