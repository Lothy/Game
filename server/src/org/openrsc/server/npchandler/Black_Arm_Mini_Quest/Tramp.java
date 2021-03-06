/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler.Black_Arm_Mini_Quest;

import org.openrsc.server.Config;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.InvItem;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;

public class Tramp implements NpcHandler {
	
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		final String[] messages99 = {"Spare some change guv?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages99, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options45 = {"Sorry I haven't got any", "Go get a job", "Ok here you go", "Is there anything down this alleyway?"};
						owner.setBusy(false);
						owner.sendMenu(options45);
						owner.setMenuHandler(new MenuHandler(options45) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										gotAny(npc, owner);
										break;
									case 1:
										getAJob(npc, owner);
										break;
									case 2:
										hereYouGo(npc, owner);
										break;
									case 3:
										thisAlley(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}


	private void gotAny(final Npc npc, final Player owner) {
		final String[] messages100 = {"Thanks anyway"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages100) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void getAJob(final Npc npc, final Player owner) {
		final String[] messages101 = {"You startin?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages101) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void hereYouGo(final Npc npc, final Player owner) {
		if(owner.getInventory().remove(new InvItem(10, 1)) > 0) {
			owner.sendInventory();
			final String[] messages102 = {"Thankyou, thats great"};
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages102) {
				public void finished() {
					World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
						public void action() {
							final String[] options46 = {"No problem", "So don't I get some sort of quest hit or something now"};
							owner.setBusy(false);
							owner.sendMenu(options46);
							owner.setMenuHandler(new MenuHandler(options46) {
								public void handleReply(final int option, final String reply) {
									owner.setBusy(true);
									for(Player informee : owner.getViewArea().getPlayersInView()) {
										informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
									}
									switch(option) {
										case 0:
											noProblem(npc, owner);
											break;
										case 1:
											hint(npc, owner);
											break;
									}
								}
							});
						}
					});
				}
			});
		} else {
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Oh, sorry I don't have any"}) {
				public void finished() {
					gotAny(npc, owner);
				}
			});
		}
	}

	private void noProblem(final Npc npc, final Player owner) {
		owner.setBusy(false);
		npc.unblock();
	}

	private void hint(final Npc npc, final Player owner) {
		final String[] messages103 = {"No that's not why I'm asking for money", "I just need to eat"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages103) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void thisAlley(final Npc npc, final Player owner) {
		final String[] messages104 = {"Yes, there is actually", "A notorious gang of thieves and hoodlums", "Called the blackarm gang"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages104) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options47 = {"Thanks for the warning", "Do you think they would let me join?"};
						owner.setBusy(false);
						owner.sendMenu(options47);
						owner.setMenuHandler(new MenuHandler(options47) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										warning(npc, owner);
										break;
									case 1:
                                        Quest phoenix = owner.getQuest(Config.Quests.JOIN_PHOENIX_GANG);
                                        Quest blackarm = owner.getQuest(Config.Quests.JOIN_BLACKARM_GANG);
										if(blackarm != null) {
											if(blackarm.finished()) {
												letMeJoin(npc, owner, true, false);
											} else {
												if(phoenix != null) {
													if(phoenix.finished()) {
														letMeJoin(npc, owner, false, true);
													} else {
														letMeJoin(npc, owner, false, false);
													}
												} else {
													letMeJoin(npc, owner, false, false);
												}
												
											}
										} else {
											if(phoenix != null) {
												if(phoenix.finished()) {
													letMeJoin(npc, owner, false, true);
												} else {
													letMeJoin(npc, owner, false, false);
												}
											} else {
												letMeJoin(npc, owner, false, false);
											}
										}
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void warning(final Npc npc, final Player owner) {
		final String[] messages105 = {"Don't worry about it"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages105) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void letMeJoin(final Npc npc, final Player owner, boolean blackarm, boolean phoenix) {
		String[] reply;
		if(blackarm) {
			reply = new String[] {"I was under the impression you were already a member"};
		} else if(phoenix) {
			reply = new String[] {"No", "You're a collaborator with the phoenix gang", "There's no way they'll let you join"};
		} else {
			reply = new String[] {"You never know", "You'll find a lady down there called Katrine", "Speak to her", "But don't upset her, she's pretty dangerous"};
		}
		if(owner.isPhoenix()) {
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, reply) {
				public void finished() {
					World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
						public void action() {
							final String[] options48 = {"How did you know I was in the phoenix gang?", "Any ideas how I could get in there then?"};
							owner.setBusy(false);
							owner.sendMenu(options48);
							owner.setMenuHandler(new MenuHandler(options48) {
								public void handleReply(final int option, final String reply) {
									owner.setBusy(true);
									for(Player informee : owner.getViewArea().getPlayersInView()) {
										informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
									}
									switch(option) {
										case 0:
											howDid(npc, owner);
											break;
										case 1:
											anyIdeas(npc, owner);
											break;
									}
								}
							});
						}
					});
				}
			});
		} else {
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, reply) {
				public void finished() {
					if(!owner.isBlackarm()) {
						owner.addQuest(Config.Quests.JOIN_BLACKARM_GANG, 0);
					}
					owner.setBusy(false);
					npc.unblock();
				}
			});
		}
	}

	private void howDid(final Npc npc, final Player owner) {
		final String[] messages107 = {"I spend a lot of time on the streets", "And you hear those sorta things sometimes"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages107) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void anyIdeas(final Npc npc, final Player owner) {
		final String[] messages108 = {"Hmm I dunno", "Your best bet would probably be to get someone else", "Someone who isn't a member of the phoenix gang", "To infiltrate the ranks of the black arm gang", "If you find someone", "Tell em to come to me first"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages108) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options49 = {"Ok good plan", "Like who?"};
						owner.setBusy(false);
						owner.sendMenu(options49);
						owner.setMenuHandler(new MenuHandler(options49) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										goodPlan(npc, owner);
										break;
									case 1:
										likeWho(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void goodPlan(final Npc npc, final Player owner) {
		owner.setBusy(false);
		npc.unblock();
	}

	private void likeWho(final Npc npc, final Player owner) {
		final String[] messages109 = {"There's plenty of other adventurers about", "Besides yourself", "I'm sure if you asked one of them nicely", "They would help you"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages109) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
}