/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler.Ernest_The_Chicken;

import org.openrsc.server.Config;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.World;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.model.Player;
import org.openrsc.server.npchandler.NpcHandler;

public class Veronica implements NpcHandler {
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Config.Quests.ERNEST_THE_CHICKEN);
		if(q != null) {
			if(q.finished()) {
				questFinished(npc, owner);
			} else {
				switch(q.getStage()) {
					case 0:
						haveYouFound(npc, owner);
						break;						
					case 1:
						talkedToProfessor(npc, owner);
					default:
						owner.setBusy(false);
						npc.unblock();
				}
			}
		} else {
			questNotStarted(npc, owner);
		}
	}
	
	private final void haveYouFound(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Have you found my sweetheart yet?"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"No, not yet"}) {
					public void finished() {
						owner.setBusy(false);
						npc.unblock();
					}
				});
			}
		});
	}
	
	private final void questFinished(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Thank you for rescuing Ernest"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Where is he now?"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Oh he went off to talk to some green warty guy", "I'm sure he'll be back soon"}) {
							public void finished() {
								owner.setBusy(false);
								npc.unblock();
							}
						});
					}
				});
			}
		});
	}
	
	private final void talkedToProfessor(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Have you found my sweetheart yet?"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Yes, he's a chicken"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I know he's not exactly brave", "But I think you're being a little harsh"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"No no he's been turned into an actual chicken", "by a mad scientist"}) {
									public void finished() {
										World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
											public void action() {
												owner.sendMessage("Veronica lets out an ear piercing shreek");
												World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Eeeeek", "My poor darling", "Why must these things happen to us?"}) {
													public void finished() {
														World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Well I'm doing my best to turn him back"}) {
															public void finished() {
																World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well be quick", "I'm sure being a chicken can't be good for him"}) {
																	public void finished() {
																		owner.setBusy(false);
																		npc.unblock();
																	}
																});
															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	private final void questNotStarted(final Npc npc, final Player owner) {
		final String[] messages5 = {"Can you please help me?", "I'm in a terrible spot of trouble"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages5, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options1 = {"Aha, sounds like a quest, I'll help", "No, I'm looking for something to kill"};
						owner.setBusy(false);
						owner.sendMenu(options1);
						owner.setMenuHandler(new MenuHandler(options1) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										final String[] messages6 = {"Yes yes I suppose it is a quest", "My fiance Ernest and I came upon this house here", "Seeing as we were a little lost", "Ernest decided to go in and ask for directions", "That was an hour ago", "That house looks very spooky", "Can you go and see if you can find him for me?"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages6) {
											public void finished() {
												final String[] messages7 = {"Ok, I'll see what I can do"};
												World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages7) {
													public void finished() {
														final String[] messages8 = {"Thank you, thank you", "I'm very grateful"};
														World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages8) {
															public void finished() {
																owner.addQuest(Config.Quests.ERNEST_THE_CHICKEN, 4);
																owner.setBusy(false);
																npc.unblock();
															}
														});
													}
												});
											}
										});
										break;
									case 1:
										final String[] messages9 = {"Oooh you violent person you"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
										break;
								}
							}
						});
					}
				});
			}
		});
	}
}