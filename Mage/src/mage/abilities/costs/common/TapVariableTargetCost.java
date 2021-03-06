/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.abilities.costs.common;

import java.util.Iterator;
import java.util.UUID;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TapVariableTargetCost extends CostImpl<TapVariableTargetCost> implements VariableCost {

    protected int amountPaid = 0;
    protected TargetControlledPermanent target;

    public TapVariableTargetCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "tap X " + target.getTargetName() + " you control";
    }

    public TapVariableTargetCost(final TapVariableTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
        this.amountPaid = cost.amountPaid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        amountPaid = 0;
        target.clearChosen();
        if (target.canChoose(sourceId, controllerId, game) && target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (Iterator it = target.getTargets().iterator(); it.hasNext();) {
                UUID uuid = (UUID) it.next();
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null && permanent.tap(game)) {
                    amountPaid++;
                }
            }
        }
        paid = true;
        return true;
    }

    @Override
    public void clearPaid() {
        paid = false;
        amountPaid = 0;
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }

    /**
     * Not Supported
     * @param filter
     */
    @Override
    public void setFilter(FilterMana filter) {
    }

    /**
     * Not supported
     * @return
     */
    @Override
    public FilterMana getFilter() {
        return new FilterMana();
    }

    @Override
    public TapVariableTargetCost copy() {
        return new TapVariableTargetCost(this);
    }

}
