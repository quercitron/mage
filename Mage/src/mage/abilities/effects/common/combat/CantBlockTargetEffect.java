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
package mage.abilities.effects.common.combat;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author North
 */
public class CantBlockTargetEffect extends RestrictionEffect<CantBlockTargetEffect> {

    public CantBlockTargetEffect(Duration duration) {
        super(duration);
    }

    public CantBlockTargetEffect(final CantBlockTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (this.targetPointer.getTargets(game, source).contains(permanent.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public CantBlockTargetEffect copy() {
        return new CantBlockTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (mode.getTargets().isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" ");
        }
        sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append("s");
        }

        sb.append(" can't block");
        if (Duration.EndOfTurn.equals(this.duration)) {
            sb.append(" this turn");
        }

        return sb.toString();
    }
}
