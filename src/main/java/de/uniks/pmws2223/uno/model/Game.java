package de.uniks.pmws2223.uno.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Game
{
   public static final String PROPERTY_DIRECTION = "direction";
   public static final String PROPERTY_DISCARD_PILE = "discardPile";
   public static final String PROPERTY_PLAYERS = "players";
   public static final String PROPERTY_CURRENT_PLAYER = "currentPLayer";
   private boolean direction;
   protected PropertyChangeSupport listeners;
   private Card discardPile;
   private List<Player> players;
   private Player currentPLayer;

   public boolean isDirection()
   {
      return this.direction;
   }

   public Game setDirection(boolean value)
   {
      if (value == this.direction)
      {
         return this;
      }

      final boolean oldValue = this.direction;
      this.direction = value;
      this.firePropertyChange(PROPERTY_DIRECTION, oldValue, value);
      return this;
   }

   public Card getDiscardPile()
   {
      return this.discardPile;
   }

   public Game setDiscardPile(Card value)
   {
      if (this.discardPile == value)
      {
         return this;
      }

      final Card oldValue = this.discardPile;
      if (this.discardPile != null)
      {
         this.discardPile = null;
         oldValue.setGame(null);
      }
      this.discardPile = value;
      if (value != null)
      {
         value.setGame(this);
      }
      this.firePropertyChange(PROPERTY_DISCARD_PILE, oldValue, value);
      return this;
   }

   public List<Player> getPlayers()
   {
      return this.players != null ? Collections.unmodifiableList(this.players) : Collections.emptyList();
   }

   public Game withPlayers(Player value)
   {
      if (this.players == null)
      {
         this.players = new ArrayList<>();
      }
      if (!this.players.contains(value))
      {
         this.players.add(value);
         value.setGame(this);
         this.firePropertyChange(PROPERTY_PLAYERS, null, value);
      }
      return this;
   }

   public Game withPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Player value)
   {
      if (this.players != null && this.players.remove(value))
      {
         value.setGame(null);
         this.firePropertyChange(PROPERTY_PLAYERS, value, null);
      }
      return this;
   }

   public Game withoutPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Player getCurrentPLayer()
   {
      return this.currentPLayer;
   }

   public Game setCurrentPLayer(Player value)
   {
      if (this.currentPLayer == value)
      {
         return this;
      }

      final Player oldValue = this.currentPLayer;
      this.currentPLayer = value;
      this.firePropertyChange(PROPERTY_CURRENT_PLAYER, oldValue, value);
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

   public void removeYou()
   {
      this.setDiscardPile(null);
      this.withoutPlayers(new ArrayList<>(this.getPlayers()));
      this.setCurrentPLayer(null);
   }
}
