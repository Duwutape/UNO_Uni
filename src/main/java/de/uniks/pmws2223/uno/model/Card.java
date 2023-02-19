package de.uniks.pmws2223.uno.model;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class Card {
    public static final String PROPERTY_VALUE = "value";
    public static final String PROPERTY_COLOR = "color";
    public static final String PROPERTY_GAME = "game";
    public static final String PROPERTY_PLAYER = "player";
    private String value;
    private String color;
    protected PropertyChangeSupport listeners;
    private Game game;
    private Player player;

    public Card(String value, String color) {
        this.value = value;
        this.color = color;
    }

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
      return result.substring(1);
   }

    public void removeYou()
   {
      this.setGame(null);
      this.setPlayer(null);
   }
}
