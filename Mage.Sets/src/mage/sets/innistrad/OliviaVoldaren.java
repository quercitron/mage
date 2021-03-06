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
package mage.sets.innistrad;

import java.util.UUID;

import mage.constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public class OliviaVoldaren extends CardImpl<OliviaVoldaren> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    private static final FilterCreaturePermanent vampireFilter = new FilterCreaturePermanent("Vampire");

    static {
        filter.add(new AnotherPredicate());
        vampireFilter.add(new SubtypePredicate("Vampire"));
    }

    public OliviaVoldaren(UUID ownerId) {
        super(ownerId, 215, "Olivia Voldaren", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "ISD";
        this.supertype.add("Legendary");
        this.subtype.add("Vampire");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        String rule = "Gain control of target Vampire for as long as you control Olivia Voldaren";

        FilterPermanent filter2 = new FilterPermanent();
        filter2.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new CardIdPredicate(this.getId()));

        this.addAbility(FlyingAbility.getInstance());

        // {1}{R}: Olivia Voldaren deals 1 damage to another target creature. That creature becomes a Vampire in addition to its other types. Put a +1/+1 counter on Olivia Voldaren.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{1}{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new AddCardSubTypeTargetEffect("Vampire", Duration.WhileOnBattlefield));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);

        // {3}{B}{B}: Gain control of target Vampire for as long as you control Olivia Voldaren.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(new GainControlTargetEffect(Duration.Custom), new ControlsPermanentCondition(filter2), rule);
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{3}{B}{B}"));
        ability2.addTarget(new TargetCreaturePermanent(vampireFilter));
        this.addAbility(ability2);
    }

    public OliviaVoldaren(final OliviaVoldaren card) {
        super(card);
    }

    @Override
    public OliviaVoldaren copy() {
        return new OliviaVoldaren(this);
    }
}
