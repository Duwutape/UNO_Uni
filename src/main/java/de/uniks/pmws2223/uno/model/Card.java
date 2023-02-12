package de.uniks.pmws2223.uno.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

@SuppressWarnings({"unused", "UnusedReturnValue", "ConstantValue"})
public class Card
{
   public static final String PROPERTY_VALUE = "value";
   public static final String PROPERTY_COLOR = "color";
   public static final String PROPERTY_CAN_PLAYED_AT = "canPlayedAt";
   public static final String PROPERTY_CAN_RECEIVE = "canReceive";
   public static final String PROPERTY_GAME = "game";
   public static final String PROPERTY_PLAYER = "player";
   private String value;
   private String color;
   private List<String> canPlayedAt;
   protected PropertyChangeSupport listeners;
   private List<String> canReceive;
   private Game game;
   private Player player;

   public String getValue()
   {
      return this.value;
   }

   public Card setValue(String value)
   {
      if (Objects.equals(value, this.value))
      {
         return this;
      }

      final String oldValue = this.value;
      this.value = value;
      this.firePropertyChange(PROPERTY_VALUE, oldValue, value);
      return this;
   }

   public String getColor()
   {
      return this.color;
   }

   public Card setColor(String value)
   {
      if (Objects.equals(value, this.color))
      {
         return this;
      }

      final String oldValue = this.color;
      this.color = value;
      this.firePropertyChange(PROPERTY_COLOR, oldValue, value);
      return this;
   }

   public List<String> getCanPlayedAt()
   {
      return this.canPlayedAt != null ? Collections.unmodifiableList(this.canPlayedAt) : Collections.emptyList();
   }

   public Card withCanPlayedAt(String value)
   {
      if (this.canPlayedAt == null)
      {
         this.canPlayedAt = new ArrayList<>();
      }
      if (this.canPlayedAt.add(value))
      {
         this.firePropertyChange(PROPERTY_CAN_PLAYED_AT, null, value);
      }
      return this;
   }

   public Card withCanPlayedAt(String... value)
   {
      for (final String item : value)
      {
         this.withCanPlayedAt(item);
      }
      return this;
   }

   public Card withCanPlayedAt(Collection<? extends String> value)
   {
      for (final String item : value)
      {
         this.withCanPlayedAt(item);
      }
      return this;
   }

   public Card withoutCanPlayedAt(String value)
   {
      if (this.canPlayedAt != null && this.canPlayedAt.removeAll(Collections.singleton(value)))
      {
         this.firePropertyChange(PROPERTY_CAN_PLAYED_AT, value, null);
      }
      return this;
   }

   public Card withoutCanPlayedAt(String... value)
   {
      for (final String item : value)
      {
         this.withoutCanPlayedAt(item);
      }
      return this;
   }

   public Card withoutCanPlayedAt(Collection<? extends String> value)
   {
      for (final String item : value)
      {
         this.withoutCanPlayedAt(item);
      }
      return this;
   }

   public List<String> getCanReceive()
   {
      return this.canReceive != null ? Collections.unmodifiableList(this.canReceive) : Collections.emptyList();
   }

   public Card withCanReceive(String value)
   {
      if (this.canReceive == null)
      {
         this.canReceive = new ArrayList<>();
      }
      if (this.canReceive.add(value))
      {
         this.firePropertyChange(PROPERTY_CAN_RECEIVE, null, value);
      }
      return this;
   }

   public Card withCanReceive(String... value)
   {
      for (final String item : value)
      {
         this.withCanReceive(item);
      }
      return this;
   }

   public Card withCanReceive(Collection<? extends String> value)
   {
      for (final String item : value)
      {
         this.withCanReceive(item);
      }
      return this;
   }

   public Card withoutCanReceive(String value)
   {
      if (this.canReceive != null && this.canReceive.removeAll(Collections.singleton(value)))
      {
         this.firePropertyChange(PROPERTY_CAN_RECEIVE, value, null);
      }
      return this;
   }

   public Card withoutCanReceive(String... value)
   {
      for (final String item : value)
      {
         this.withoutCanReceive(item);
      }
      return this;
   }

   public Card withoutCanReceive(Collection<? extends String> value)
   {
      for (final String item : value)
      {
         this.withoutCanReceive(item);
      }
      return this;
   }

   public Game getGame()
   {
      return this.game;
   }

   public Card setGame(Game value)
   {
      if (this.game == value)
      {
         return this;
      }

      final Game oldValue = this.game;
      if (this.game != null)
      {
         this.game = null;
         oldValue.setDiscardPile(null);
      }
      this.game = value;
      if (value != null)
      {
         value.setDiscardPile(this);
      }
      this.firePropertyChange(PROPERTY_GAME, oldValue, value);
      return this;
   }

   public Player getPlayer()
   {
      return this.player;
   }

   public Card setPlayer(Player value)
   {
      if (this.player == value)
      {
         return this;
      }

      final Player oldValue = this.player;
      if (this.player != null)
      {
         this.player = null;
         oldValue.withoutCards(this);
      }
      this.player = value;
      if (value != null)
      {
         value.withCards(this);
      }
      this.firePropertyChange(PROPERTY_PLAYER, oldValue, value);
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
      result.append(' ').append(this.getValue());
      result.append(' ').append(this.getColor());
      result.append(' ').append(this.getCanPlayedAt());
      result.append(' ').append(this.getCanReceive());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGame(null);
      this.setPlayer(null);
   }
}
