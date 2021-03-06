/**
a* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler;

import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.World;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.model.Player;
import org.openrsc.server.npchandler.NpcHandler;
public class Banker implements NpcHandler {
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		final String[] messages8 = {"Good day, how may I help you?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages8, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options9 = {"I'd like to access my bank account please", "What is this place?"};
						owner.setBusy(false);
						owner.sendMenu(options9);
						owner.setMenuHandler(new MenuHandler(options9) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										final String[] messages9 = {"Certainly " + (owner.isMale() ? "sir" : "miss")};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
												owner.setAccessingBank(true);
												owner.showBank();
											}
										});
										break;
									/*
									case 1:
										owner.setBusy(false);
										npc.unblock();
										owner.setInAuctionHouse(true);
										owner.getActionSender().openAuctionHouse();
									break;
									*/
									case 1:
										final String[] messages10 = {"This is a branch of the bank of Runescape", "We have braches in many towns"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages10) {
											public void finished() {
												World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
													public void action() {
														final String[] options10 = {"And what do you do?", "Didn't you used to be called the bank of Varrock"};
														owner.setBusy(false);
														owner.sendMenu(options10);
														owner.setMenuHandler(new MenuHandler(options10) {
															public void handleReply(final int option, final String reply) {
																owner.setBusy(true);
																for(Player informee : owner.getViewArea().getPlayersInView()) {
																	informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
																}
																switch(option) {
																	case 0:
																		final String[] messages11 = {"We will look after your items and money for you", "So leave your valuables with us if you want to keep them safe"};
																		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages11) {
																			public void finished() {
																				owner.setBusy(false);
																				npc.unblock();
																			}
																		});
																		break;
																	case 1:
																		final String[] messages12 = {"Yes we did, but people kept coming into our branches outside of varrock", "And telling us our signs were wrong", "As if we didn't know what town we were in or something!"};
																		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages12) {
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