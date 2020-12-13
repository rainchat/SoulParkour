# SoulParkour
Parkour plugin, now jumping is fun



# Permissions
  soulparkour.use.*:
    default: op
    
    description: Gives the player access to all baseline permissions originally meant for a casual player.
    
    children:
      soulparkour.use.doublejump: true 
      // double jump
      soulparkour.use.bounces: true
      // bouncing off the wall
      soulparkour.use.grab: true 
      // bouncing off the wall
      soulparkour.use.leapoffaith: true 
      // climbing up the wall
  soulparkour.admin.*:
    default: op
    description: only for admins.
    children:
      soulparkour.admin.reload: true 
      // reload command /sp reload
