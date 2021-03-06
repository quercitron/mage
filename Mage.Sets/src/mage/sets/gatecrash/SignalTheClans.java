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
package mage.sets.gatecrash;

import java.util.List;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class SignalTheClans extends CardImpl<SignalTheClans> {

    public SignalTheClans (UUID ownerId) {
        super(ownerId, 194, "Signal the Clans", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{R}{G}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Search your library for three creature cards and reveal them. If you reveal three cards with different names, choose one of them at random and put that card into your hand. Shuffle the rest into your library.
        this.getSpellAbility().addEffect(new SignalTheClansEffect());
    }

    public SignalTheClans(final SignalTheClans card) {
        super(card);
    }

    @Override
    public SignalTheClans  copy() {
        return new SignalTheClans(this);
    }
}

class SignalTheClansEffect extends SearchEffect<SignalTheClansEffect> {


    public SignalTheClansEffect() {
        super(new TargetCardInLibrary(3, new FilterCreatureCard()), Outcome.DrawCard);
        staticText = "Search your library for three creature cards and reveal them. If you reveal three cards with different names, choose one of them at random and put that card into your hand. Shuffle the rest into your library";
    }

    public SignalTheClansEffect(final SignalTheClansEffect effect) {
        super(effect);
    }

    @Override
    public SignalTheClansEffect copy() {
        return new SignalTheClansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        //Search your library for three creature cards
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards cards = new CardsImpl();
                for (UUID cardId: (List<UUID>)target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null){
                        cards.add(card);
                    }
                }
                //Reveal them
                player.revealCards("Reveal", cards, game);
                Card cardsArray[] = cards.getCards(game).toArray(new Card[0]);
                //If you reveal three cards with different names
                if(cardsArray.length == 3 && !cardsArray[0].getName().equals(cardsArray[1]) && !cardsArray[0].getName().equals(cardsArray[2]) && !cardsArray[1].getName().equals(cardsArray[2])){
                    //Choose one of them at random and put that card into your hand
                    Card randomCard = cards.getRandom(game);
                    randomCard.moveToZone(Zone.HAND, source.getId(), game, true);
                    cards.remove(randomCard);
                }
                //Shuffle the rest into your library
                for(Card card : cards.getCards(game)){
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }

}
