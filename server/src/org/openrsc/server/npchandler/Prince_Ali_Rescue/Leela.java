/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler.Prince_Ali_Rescue;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.InvItem;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.World;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.npchandler.NpcHandler;

import java.util.ArrayList;
import org.openrsc.server.Config;

public class Leela implements NpcHandler {

	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Config.Quests.PRINCE_ALI_RESCUE);
		if(q != null) {
			if(q.finished()) {
				questFinished(npc, owner);
			} else {
				switch(q.getStage()) {
					case 0:
						questNotStarted(npc, owner);
						break;
					case 1:
							questJustStarted(npc, owner);
						break;
					case 2:
						if(owner.leelaHasKey()) {
							World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"My father sent this key for you, be careful not to lose it"}, true) {
								public void finished() {
									owner.sendMessage("Leela gives you a copy of the key to the princes door");
									owner.getInventory().add(new InvItem(242, 1));
									owner.sendInventory();
									owner.takeLeelaKey();
									owner.setBusy(false);
									npc.unblock();
								}
							});
						} else {
							keyWasMade(npc, owner);
						}
						break;
					case 3:
						guardIsDrunk(npc, owner);
						break;
					case 4:
						keliTiedUp(npc, owner);
						break;
					case 5:
						questFinished(npc, owner);
				}
			}
		} else {
			questNotStarted(npc, owner);
		}
	}

	private final void keliTiedUp(final Npc npc, final Player owner) {
		final String[] messages0 = {"You should carry one, get in and rescue the prince", "Keli is sharp, she won't stay tied up for long"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				if(owner.getInventory().countId(242) == 0) {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I've lost the key to the prince's cell door"}) {
						public void finished() {
							if(owner.leelaHasKey()) {
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"My father sent this key for you, be careful not to lose it"}, true) {
									public void finished() {
										owner.sendMessage("Leela gives you a copy of the key to the princes door");
										owner.getInventory().add(new InvItem(242, 1));
										owner.sendInventory();
										owner.takeLeelaKey();
										owner.setBusy(false);
										npc.unblock();
									}
								});
							} else {
								final String[] messages9 = {"Then take the print, with bronze, to my father"};
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
									public void finished() {
										owner.setBusy(false);
										npc.unblock();
									}
								});
							}
						}
					});
				} else {
					owner.setBusy(false);
					npc.unblock();
				}
			}
		});
	}
	
	private final void guardIsDrunk(final Npc npc, final Player owner) {
		final String[] messages0 = {"Great! the guard is now harmless", "Now you just need to use the rope on Keli to remove her", "Then you can go in and give everything to the prince"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				if(owner.getInventory().countId(242) == 0) {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I've lost the key to the prince's cell door"}) {
						public void finished() {
							if(owner.leelaHasKey()) {
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"My father sent this key for you, be careful not to lose it"}, true) {
									public void finished() {
										owner.sendMessage("Leela gives you a copy of the key to the princes door");
										owner.getInventory().add(new InvItem(242, 1));
										owner.sendInventory();
										owner.takeLeelaKey();
										owner.setBusy(false);
										npc.unblock();
									}
								});
							} else {
								final String[] messages9 = {"Then take the print, with bronze, to my father"};
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
									public void finished() {
										owner.setBusy(false);
										npc.unblock();
									}
								});
							}
						}
					});
				} else {
					owner.setBusy(false);
					npc.unblock();
				}
			}
		});
	}

	private final void keyWasMade(final Npc npc, final Player owner) {
		if(owner.getInventory().countId(244) > 0 && owner.getInventory().countId(194) > 0 && owner.getInventory().countId(240) > 0 && owner.getInventory().countId(237) > 0) {
			if(owner.getInventory().countId(242) > 0) {
				hasEverything(npc, owner);
			} else {
				questJustStartedNoKeyAllDisguise(npc, owner);
			}
		} else {
			if(owner.getInventory().countId(242) > 0) {
				questJustStartedKeyNoDisguise(npc, owner);
			} else {
				questJustStarted(npc, owner);
			}
		}
	}

	private final void questJustStartedKeyNoDisguise(final Npc npc, final Player owner) {
		final String[] messages6 = {"I am here to help you free the Prince"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages6, true) {
			public void finished() {
				final String[] messages7 = {"Your employment is known to me.", "Now, do you know all that we need to make the break?"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages7) {
					public void finished() {
						World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
							public void action() {
								final String[] options4 = {"I must make a disguise. What do you suggest?", "What can i do with the guards?", "I will go and get the rest of the escape equipment"};
								owner.setBusy(false);
								owner.sendMenu(options4);
								owner.setMenuHandler(new MenuHandler(options4) {
									public void handleReply(final int option, final String reply) {
										owner.setBusy(true);
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										switch(option) {
											case 0:
												disguise(npc, owner);
												break;
											case 1:
												guards(npc, owner);
												break;
											case 2:
												willGo(npc, owner);
												break;
										}
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	private final void questJustStartedNoKeyAllDisguise(final Npc npc, final Player owner) {
		final String[] messages6 = {"I am here to help you free the Prince"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages6, true) {
			public void finished() {
				final String[] messages7 = {"Your employment is known to me.", "Now, do you know all that we need to make the break?"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages7) {
					public void finished() {
						World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
							public void action() {
								final String[] options4 = {"I need to get the key made", "What can i do with the guards?", "I will go and get the rest of the escape equipment"};
								owner.setBusy(false);
								owner.sendMenu(options4);
								owner.setMenuHandler(new MenuHandler(options4) {
									public void handleReply(final int option, final String reply) {
										owner.setBusy(true);
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										switch(option) {
											case 0:
												keyMade(npc, owner);
												break;
											case 1:
												guards(npc, owner);
												break;
											case 2:
												willGo(npc, owner);
												break;
										}
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	private final void hasEverything(final Npc npc, final Player owner) {
		final String[] messages0 = {"Good, you have all the basic equipment", "What are your plans to stop the guard interfering?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options0 = {"I haven't spoken to him yet", "I was going to attack him", "I hoped to get him drunk", "Maybe I could bribe him to leave"};
						owner.setBusy(false);
						owner.sendMenu(options0);
						owner.setMenuHandler(new MenuHandler(options0) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										haventSpoken(npc, owner);
										break;
									case 1:
										attack(npc, owner);
										break;
									case 2:
										drunk(npc, owner);
										break;
									case 3:
										bribe(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private final void questJustStarted(final Npc npc, final Player owner) {
		final String[] messages6 = {"I am here to help you free the Prince"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages6, true) {
			public void finished() {
				final String[] messages7 = {"Your employment is known to me.", "Now, do you know all that we need to make the break?"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages7) {
					public void finished() {
						World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
							public void action() {
								final String[] options4 = {"I must make a disguise. What do you suggest?", "I need to get the key made", "What can i do with the guards?", "I will go and get the rest of the escape equipment"};
								owner.setBusy(false);
								owner.sendMenu(options4);
								owner.setMenuHandler(new MenuHandler(options4) {
									public void handleReply(final int option, final String reply) {
										owner.setBusy(true);
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										switch(option) {
											case 0:
												disguise(npc, owner);
												break;
											case 1:
												keyMade(npc, owner);
												break;
											case 2:
												guards(npc, owner);
												break;
											case 3:
												willGo(npc, owner);
												break;
										}
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private final void questFinished(final Npc npc, final Player owner) {
		final String[] messages0 = {"Thankyou, Al Kharid will forever owe you for your help", "I think that if there is ever anything that needs to be done,", "you will be someone they can rely on"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private final void questNotStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"What are you waiting here for"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"That is no concern of yours, adventurer"}) {
					public void finished() {
						owner.setBusy(false);
						npc.unblock();
					}
				});
			}
		});
	}

	private void disguise(final Npc npc, final Player owner) {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Only the lady Keli, can wander outside the jail");
		strings.add("The guards will shoot to kill if they see the prince out");
		strings.add("So we need a disguise well enough to fool them at a distance");
		boolean b1 = owner.getInventory().countId(244) > 0;
		boolean b2 = owner.getInventory().countId(194) > 0;
		boolean b3 = owner.getInventory().countId(240) > 0;
		boolean b4 = owner.getInventory().countId(237) > 0;
		boolean b5 = true;
		if(b1) {
			strings.add("The wig you have got, well done");
		} else {
			b5 = false;
			strings.add("You need a wig, maybe made from wool");
			strings.add("If you can find someone who can work with wool, ask them about it");
			strings.add("Then the old witch may be able to help you dye it");		
		}
		if(b2) {
			strings.add("You have got the skirt, good");
		} else {
			b5 = false;
			strings.add("You will need to get a pink skirt, same as Keli's");
		}
		if(b3) {
			strings.add("You have got the skin paint, well done");
			strings.add("I thought you would struggle to make that");
		} else {
			b5 = false;
			strings.add("we still need something to colour the Princes skin lighter");
			strings.add("Theres an old witch close to here, she knows about many things");
			strings.add("She may know some way to make the skin lighter");		
		}
		if(b5) {
			strings.add("You do have everything for the disguise");
		}
		if(b4) {
			strings.add("You have rope I see, tie up Keli");
			strings.add("That will be the most dangerous part of the plan");
		} else {
			strings.add("You will still need some rope to tie up Keli, of course");
			strings.add("I heard that there was a good ropemaker around here");
		}
		String[] disguise = new String[strings.size()];
		strings.toArray(disguise);
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, disguise) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						if(owner.getInventory().countId(242) > 0) {
							final String[] options5 = {"What can I do with the guards?", "I will go and get the rest of the escape equipment"};
							owner.setBusy(false);
							owner.sendMenu(options5);
							owner.setMenuHandler(new MenuHandler(options5) {
								public void handleReply(final int option, final String reply) {
									owner.setBusy(true);
									for(Player informee : owner.getViewArea().getPlayersInView()) {
										informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
									}
									switch(option) {
										case 0:
											guards(npc, owner);
											break;
										case 1:
											willGo(npc, owner);
											break;
									}
								}
							});
						} else {
							final String[] options5 = {"I need to get the key made", "What can I do with the guards?", "I will go and get the rest of the escape equipment"};
							owner.setBusy(false);
							owner.sendMenu(options5);
							owner.setMenuHandler(new MenuHandler(options5) {
								public void handleReply(final int option, final String reply) {
									owner.setBusy(true);
									for(Player informee : owner.getViewArea().getPlayersInView()) {
										informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
									}
									switch(option) {
										case 0:
											keyMade(npc, owner);
											break;
										case 1:
											guards(npc, owner);
											break;
										case 2:
											willGo(npc, owner);
											break;
									}
								}
							});
						}
					}
				});
			}
		});
	}

	private void keyMade(final Npc npc, final Player owner) {
		final String[] messages9 = {"Yes, that is most important", "There is no way you can get the real key.", "It is on a chain around Keli's neck. almost impossible to steal", "get some soft clay, and get her to show you the key somehow", "then take the print, with bronze, to my father"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						if(owner.getInventory().countId(244) > 0 && owner.getInventory().countId(194) > 0 && owner.getInventory().countId(240) > 0 && owner.getInventory().countId(237) > 0) {
							final String[] options6 = {"What can i do with the guards?", "I will go and get the rest of the escape equipment"};
							owner.setBusy(false);
							owner.sendMenu(options6);
							owner.setMenuHandler(new MenuHandler(options6) {
								public void handleReply(final int option, final String reply) {
									owner.setBusy(true);
									for(Player informee : owner.getViewArea().getPlayersInView()) {
										informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
									}
									switch(option) {
										case 0:
											guards(npc, owner);
											break;
										case 1:
											willGo(npc, owner);
											break;
									}
								}
							});
						} else {
							final String[] options6 = {"I must make a disguise. What do you suggest?", "What can i do with the guards?", "I will go and get the rest of the escape equipment"};
							owner.setBusy(false);
							owner.sendMenu(options6);
							owner.setMenuHandler(new MenuHandler(options6) {
								public void handleReply(final int option, final String reply) {
									owner.setBusy(true);
									for(Player informee : owner.getViewArea().getPlayersInView()) {
										informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
									}
									switch(option) {
										case 0:
											disguise(npc, owner);
											break;
										case 1:
											guards(npc, owner);
											break;
										case 2:
											willGo(npc, owner);
											break;
									}
								}
							});
						}
					}
				});
			}
		});
	}

	private void guards(final Npc npc, final Player owner) {
		final String[] messages10 = {"Most of the guards will be easy", "The disguise will get past them", "The only guard who will be a problem will be the one at the door", "He is talkative, try to find a weakness in him", "We can discuss this more when you have the rest of the escape kit"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages10) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options7 = {"I must make a disguise. What do you suggest?", "I need to get the key made", "I will go and get the rest of the escape equipment"};
						owner.setBusy(false);
						owner.sendMenu(options7);
						owner.setMenuHandler(new MenuHandler(options7) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										disguise(npc, owner);
										break;
									case 1:
										keyMade(npc, owner);
										break;
									case 2:
										willGo(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void willGo(final Npc npc, final Player owner) {
		final String[] messages11 = {"Good, I shall await your return with everything"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages11) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void bribe(final Npc npc, final Player owner) {
		final String[] messages1 = {"You could try. I don't think the emir will pay anything towards it", "And we did bribe one of their guards once", "Keli killed him in front of the other guards, as a deterrent", "It would probably take a lot of gold", "Good luck with the guard. When the guard is out you can tie up Keli"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages1) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void attack(final Npc npc, final Player owner) {
		final String[] messages2 = {"I don't think you should", "If you do the rest of the gang and Keli would attack you", "The door guard should be removed first to make it easy", "Good luck with the guard. When the guard is out you can tie up Keli"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages2) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void haventSpoken(final Npc npc, final Player owner) {
		final String[] messages3 = {"Well, speaking to him may find a weakness he has", "See if theres something that could stop him bothering us", "Good luck with the guard. When the guard is out you can tie up Keli"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages3) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void drunk(final Npc npc, final Player owner) {
		final String[] messages4 = {"Well, thats possible. These guards do like a drink", "I would think it would take at least 3 beers to do it well", "You would probably have to do it all at the same time too", "The effects of the local beer wear off quickly", "Good luck with the guard. When the guard is out you can tie up Keli"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages4) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
}