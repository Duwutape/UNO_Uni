package de.uniks.pmws2223.uno.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class Player
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_CARDS = "cards";
   public static final String PROPERTY_GAME = "game";
   public static final String PROPERTY_TYPE = "type";
   private String name;
   protected PropertyChangeSupport listeners;
   private List<Card> cards;
   private Game game;
   private String type;

   public String getName()
   {
      return this.name;
   }

   public Player setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      return this;
   }

   public List<Card> getCards()
   {
      return this.cards != null ? Collections.unmodifiableList(this.cards) : Collections.emptyList();
   }

   public Player withCards(Card value)
   {
      if (this.cards == null)
      {
         this.cards = new ArrayList<>();
      }
      if (!this.cards.contains(value))
      {
         this.cards.add(value);
         value.setPlayer(this);
         this.firePropertyChange(PROPERTY_CARDS, null, value);
      }
      return this;
   }

   public Player withCards(Card... value)
   {
      for (final Card item : value)
      {
         this.withCards(item);
      }
      return this;
   }

   public Player withCards(Collection<? extends Card> value)
   {
      for (final Card item : value)
      {
         this.withCards(item);
      }
      return this;
   }

   public Player withoutCards(Card value)
   {
      if (this.cards != null && this.cards.remove(value))
      {
         value.setPlayer(null);
         this.firePropertyChange(PROPERTY_CARDS, value, null);
      }
      return this;
   }

   public Player withoutCards(Card... value)
   {
      for (final Card item : value)
      {
         this.withoutCards(item);
      }
      return this;
   }

   public Player withoutCards(Collection<? extends Card> value)
   {
      for (final Card item : value)
      {
         this.withoutCards(item);
      }
      return this;
   }

   public Game getGame()
   {
      return this.game;
   }

   public Player setGame(Game value)
   {
      if (this.game == value)
      {
         return this;
      }

      final Game oldValue = this.game;
      if (this.game != null)
      {
         this.game = null;
         oldValue.withoutPlayers(this);
      }
      this.game = value;
      if (value != null)
      {
         value.withPlayers(this);
      }
      this.firePropertyChange(PROPERTY_GAME, oldValue, value);
      return this;
   }

   public String getType()
   {
      return this.type;
   }

   public Player setType(String value)
   {
      if (Objects.equals(value, this.type))
      {
         return this;
      }

      final String oldValue = this.type;
      this.type = value;
      this.firePropertyChange(PROPERTY_TYPE, oldValue, value);
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public PropertyChangeSupport listeners()
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      return this.listeners;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getName());
      result.append(' ').append(this.getType());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutCards(new ArrayList<>(this.getCards()));
      this.setGame(null);
   }
}
