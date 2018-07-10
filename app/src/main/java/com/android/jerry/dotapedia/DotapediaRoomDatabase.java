package com.android.jerry.dotapedia;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Item.class, Hero.class}, version = 2)
public abstract class DotapediaRoomDatabase extends RoomDatabase {

    // DAO for table "dota_items"
    public abstract ItemDao itemDao();

    // DAO for table "dota_heroes"
    public abstract HeroDao heroDao();

    private static DotapediaRoomDatabase INSTANCE;

    // Build database
    public static DotapediaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DotapediaRoomDatabase.class) {
                if (INSTANCE == null) {
                    // create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DotapediaRoomDatabase.class, "dotapedia_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                /**
                 * Called when the database has been opened.
                 * Creates and executes an AsyncTask to add default dotapedia contents.
                 *
                 * @param db The database.
                 */
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ItemDao mItemDao;
        private final HeroDao mHeroDao;

        PopulateDbAsync(DotapediaRoomDatabase db) {
            mItemDao = db.itemDao();
            mHeroDao = db.heroDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Pre-populate Database Data
            mHeroDao.deleteAll();
            mItemDao.deleteAll();

            // Process all heroes -----------------------------------
            if (mHeroDao.getTableRowCount() == 0) {
                // SHADOW FIEND
                Hero hero = new Hero("SHADOW FIEND");
                hero.setIconPath("he_nevermore_full");
                hero.setCategory("Ranged - Carry - Nuker");
                hero.setBiography("It is said that Nevermore the Shadow Fiend has the soul of a poet, and in fact he has thousands of them. Over the ages he has claimed the souls of poets, priests, emperors, beggars, slaves, philosophers, criminals and (naturally) heroes; no sort of soul escapes him. What he does with them is unknown. No one has ever peered into the Abysm whence Nevermore reaches out like an eel from among astral rocks. Does he devour them one after another? Does he mount them along the halls of an eldritch temple, or pickle the souls in necromantic brine? Is he merely a puppet, pushed through the dimensional rift by a demonic puppeteer? Such is his evil, so intense his aura of darkness, that no rational mind may penetrate it. Of course, if you really want to know where the stolen souls go, there's one sure way to find out: Add your soul to his collection. Or just wait for Nevermore.\t");
                mHeroDao.insert(hero);

                // ANTI-MAGE
                hero.setName("ANTI-MAGE");
                hero.setIconPath("he_antimage_full");
                hero.setCategory("Melee - Carry - Escape - Nuker");
                hero.setBiography("The monks of Turstarkuri watched the rugged valleys below their mountain monastery as wave after wave of invaders swept through the lower kingdoms. Ascetic and pragmatic, in their remote monastic eyrie they remained aloof from mundane strife, wrapped in meditation that knew no gods or elements of magic. Then came the Legion of the Dead God, crusaders with a sinister mandate to replace all local worship with their Unliving Lord's poisonous nihilosophy. From a landscape that had known nothing but blood and battle for a thousand years, they tore the souls and bones of countless fallen legions and pitched them against Turstarkuri. The monastery stood scarcely a fortnight against the assault, and the few monks who bothered to surface from their meditations believed the invaders were but demonic visions sent to distract them from meditation. They died where they sat on their silken cushions. Only one youth survived--a pilgrim who had come as an acolyte, seeking wisdom, but had yet to be admitted to the monastery. He watched in horror as the monks to whom he had served tea and nettles were first slaughtered, then raised to join the ranks of the Dead God's priesthood. With nothing but a few of Turstarkuri's prized dogmatic scrolls, he crept away to the comparative safety of other lands, swearing to obliterate not only the Dead God's magic users--but to put an end to magic altogether. ");
                mHeroDao.insert(hero);

                // CHAOS KNIGHT
                hero.setName("CHAOS KNIGHT");
                hero.setIconPath("he_chaos_knight_full");
                hero.setCategory("Melee - Carry - Disabler - Durable - Pusher - Initiator");
                hero.setBiography("The veteran of countless battles on a thousand worlds, Chaos Knight hails from a far upstream plane where the fundamental laws of the universe have found sentient expression. Of all the ancient Fundamentals, he is the oldest and most tireless, endlessly searching out a being he knows only as \"The Light.\" Long ago the Light ventured out from the progenitor realm, in defiance of the first covenant. Now Chaos Knight shifts from plane to plane, always on the hunt to extinguish the Light wherever he finds it. A thousand times he has snuffed out the source, and always he slides into another plane to continue his search anew. \n" +
                        "\n" +
                        "Upon his steed Armageddon he rides, wading into battle with maniacal frenzy, drawing strength from the disorder of the universe. A physical manifestation of chaos itself, in times of need he calls upon other versions of himself from other planes, and together these dark horsemen ride into battle, as unstoppable as any force of nature. Only when the last Light of the world is scoured from existence will the search be ended. Where rides the Chaos Knight, death soon follows.\t");
                mHeroDao.insert(hero);

                // CLINKZ
                hero.setName("CLINKZ");
                hero.setIconPath("he_clinkz_full");
                hero.setCategory("Ranged - Carry - Escape - Pusher");
                hero.setBiography("At the base of the Bleeding Hills stretches a thousand-league wood, a place called The Hoven, where black pools gather the tarry blood of the uplands, and the king-mage Sutherex sits in benevolent rule. Once a sworn protector of the Hoven lands, Clinkz earned a reputation for his skill with a bow. In the three-hundredth year of the king-mage, the demon Maraxiform rose from sixth hell to lay claim to the forest. In response, the king-mage decreed an unbreakable spell: to any who slew the demon would be granted Life Without End. \n" +
                        "\n" +
                        "Unaware of the spell, Clinkz waded into battle, defending his lands against the demon's fiery onslaught. Clinkz drove Maraxiform back to the gates of sixth-hell itself, where on that fiery threshold the two locked in a mortal conflict. Grievously wounded, the demon let out a blast of hellfire as Clinkz loosed his final arrow. The arrow struck the demon true as hellfire poured out across the land, lighting the black pools and burning Clinkz alive at the instant of the demon's death. Thus, the mage's spell took effect at the very moment of the archer's conflagration, preserving him in this unholy state, leaving him a being of bones and rage, caught in the very act of dying, carrying hell's breath with him on his journey into eternity.\t");
                mHeroDao.insert(hero);

                // CRYSTAL MAIDEN
                hero.setName("CRYSTAL MAIDEN");
                hero.setIconPath("he_crystal_maiden_full");
                hero.setCategory("Ranged - Support - Disabler - Nuker - Jungler");
                hero.setBiography("Born in a temperate realm, raised with her fiery older sister Lina, Rylai the Crystal Maiden soon found that her innate elemental affinity to ice created trouble for all those around her. Wellsprings and mountain rivers froze in moments if she stopped to rest nearby; ripening crops were bitten by frost, and fruiting orchards turned to mazes of ice and came crashing down, spoiled. When their exasperated parents packed Lina off to the equator, Rylai found herself banished to the cold northern realm of Icewrack, where she was taken in by an Ice Wizard who had carved himself a hermitage at the crown of the Blueheart Glacier. After long study, the wizard pronounced her ready for solitary practice and left her to take his place, descending into the glacier to hibernate for a thousand years. Her mastery of the Frozen Arts has only deepened since that time, and now her skills are unmatched.\t");
                mHeroDao.insert(hero);

                // DARK WILLOW
                hero.setName("DARK WILLOW");
                hero.setIconPath("he_dark_willow_full");
                hero.setCategory("Ranged - Support - Nuker - Disabler - Escape");
                hero.setBiography("Children love telling stories about the whimsical adventures of fairies... That’s because children don’t know that most fairies are spiteful jerks. And in the world of spiteful fairies there are few names spoken of with more contempt than Mireska Sunbreeze.\n" +
                        "\n" +
                        "Born to a fae merchant king, Mireska grew up in Revtel; a cutthroat nation where manipulation and murder were the norm. But while she was quite adept at navigating the etiquette, unspoken laws, and social rituals that permeated every element of her life, she found the whole thing rather boring.\n" +
                        "\n" +
                        "So, Mireska did what most rebellious children do: burn down her family estate and set off with her pet wisp Jex to live the life of a wandering grifter.\t");
                mHeroDao.insert(hero);

                // DEATH PROPHET
                hero.setName("DEATH PROPHET");
                hero.setIconPath("he_death_prophet_full");
                hero.setCategory("Ranged - Carry - Pusher - Nuker - Disabler");
                hero.setBiography("Krobelus was a Death Prophet--which is one way of saying she told fortunes for the wealthiest of those who wished to look beyond the veil. But after years of inquiring on behalf of others, she began to seek clues on her own fate. When death refused to yield its secrets, she tried to buy them with her life. But the ultimate price proved insufficient. Death disgorged her again and again, always holding back its deepest mysteries. Her jealousy grew. Others could die for eternity--why not she? Why must she alone be cast back on the shores of life with such tiresome regularity? Why was she not worthy of the one thing all other living creatures took for granted? Still, she would not be discouraged. Each time she returned from the grave, she brought a bit of death back with her. Wraiths followed her like fragments of her shattered soul; her blood grew thin and ectoplasmic; the feasting creatures of twilight took her for their kin.. She gave a little of her life with every demise, and it began to seem as if her end was in sight. With her dedication to death redoubled, and no client other than herself, Krobelus threw herself ever more fervently into death's abyss, intent on fulfilling the one prophecy that eluded her: That someday the Death Prophet would return from death no more.\t");
                mHeroDao.insert(hero);

                // DROW RANGER
                hero.setName("DROW RANGER");
                hero.setIconPath("he_drow_ranger_full");
                hero.setCategory("Ranged - Carry - Disabler - Pusher");
                hero.setBiography("Drow Ranger's given name is Traxex--a name well suited to the short, trollish, rather repulsive Drow people. But Traxex herself is not a Drow. Her parents were travelers in a caravan set upon by bandits, whose noisy slaughter of innocents roused the ire of the quiet Drow people. After the battle settled, the Drow discovered a small girl-child hiding in the ruined wagons, and agreed she could not be abandoned. Even as child, Traxex showed herself naturally adept at the arts they prized: Stealth, silence, subtlety. In spirit, if not in physique, she might have been a Drow changeling, returned to her proper home. But as she grew, she towered above her family and came to think of herself as ugly. After all, her features were smooth and symmetrical, entirely devoid of warts and coarse whiskers. Estranged from her adopted tribe, she withdrew to live alone in the woods. Lost travelers who find their way from the forest sometimes speak of an impossibly beautiful Ranger who peered at them from deep among the trees, then vanished like a dream before they could approach. Lithe and stealthy, icy hot, she moves like mist in silence. That whispering you hear is her frozen arrows finding an enemy's heart. \n");
                mHeroDao.insert(hero);

                // INVOKER
                hero.setName("INVOKER");
                hero.setIconPath("he_invoker_full");
                hero.setCategory("Ranged - Carry - Nuker - Disabler - Escape - Pusher");
                hero.setBiography("In its earliest, and some would say most potent form, magic was primarily the art of memory. It required no technology, no wands or appurtenances other than the mind of the magician. All the trappings of ritual were merely mnemonic devices, meant to allow the practitioner to recall in rich detail the specific mental formulae that unlocked a spell's power. The greatest mages in those days were the ones blessed with the greatest memories, and yet so complex were the invocations that all wizards were forced to specialize. The most devoted might hope in a lifetime to have adequate recollection of three spells--four at most. Ordinary wizards were content to know two, and it was not uncommon for a village mage to know only one--with even that requiring him to consult grimoires as an aid against forgetfulness on the rare occasions when he might be called to use it. But among these early practitioners there was one exception, a genius of vast intellect and prodigious memory who came to be known as the Invoker. In his youth, the precocious wizard mastered not four, not five, not even seven incantations: He could command no fewer than ten spells, and cast them instantly. Many more he learned but found useless, and would practice once then purge from his mind forever, to make room for more practical invocations. One such spell was the Sempiternal Cantrap--a longevity spell of such power that those who cast it in the world's first days are among us still (unless they have been crushed to atoms). Most of these quasi-immortals live quietly, afraid to admit their secret: But Invoker is not one to keep his gifts hidden. He is ancient, learned beyond all others, and his mind somehow still has space to contain an immense sense of his own worth...as well as the Invocations with which he amuses himself through the long slow twilight of the world's dying days.\t");
                mHeroDao.insert(hero);

                // JUGGERNAUT
                hero.setName("JUGGERNAUT");
                hero.setIconPath("he_juggernaut_full");
                hero.setCategory("Melee - Carry - Pusher - Escape");
                hero.setBiography("No one has ever seen the face hidden beneath the mask of Yurnero the Juggernaut. It is only speculation that he even has one. For defying a corrupt lord, Yurnero was exiled from the ancient Isle of Masks--a punishment that saved his life. The isle soon after vanished beneath the waves in a night of vengeful magic. He alone remains to carry on the Isle's long Juggernaut tradition, one of ritual and swordplay. The last practitioner of the art, Yurnero's confidence and courage are the result of endless practice; his inventive bladework proves that he has never stopped challenging himself. Still, his motives are as unreadable as his expression. For a hero who has lost everything twice over, he fights as if victory is a foregone conclusion. ");
                mHeroDao.insert(hero);

                // LIFESTEALER
                hero.setName("LIFESTEALER");
                hero.setIconPath("he_life_stealer_full");
                hero.setCategory("Melee - Carry - Durable - Jungler - Escape - Disabler");
                hero.setBiography("In the dungeons of Devarque, a vengeful wizard lay in shackles, plotting his escape. He shared his cell with a gibbering creature known as N'aix, a thief cursed by the Vile Council with longevity, so that its life-sentence for theft and cozening might be as punishing as possible. Over the years, its chains had corroded, along with its sanity; N'aix retained no memory of its former life and no longer dreamt of escape. Seeing a perfect vessel for his plans, the wizard wove a spell of Infestation and cast his life-force into N'aix's body, intending to compel N'aix to sacrifice itself in a frenzy of violence while the mage returned to his body and crept away unnoticed. Instead, the wizard found his mind caught in a vortex of madness so powerful that it swept away his plans and shattered his will. Jarred to consciousness by the sudden infusion of fresh life, N'aix woke from its nightmare of madness and obeyed the disembodied voice that filled its skull, which had only the one thought: To escape. In that moment Lifestealer was born. The creature cast its mind into dungeon guards and soldiers, compelling them to open locks and cut down their companions, opening an unobstructed path to freedom while feeding on their lives. Lifestealer still wears the broken shackles as a warning that none may hold him, but on the inside remains a prisoner. Two minds inhabit the single form--a nameless creature of malevolent cunning, and the Master whose voice he pretends to obey.\t");
                mHeroDao.insert(hero);

                // LONE DRUID
                hero.setName("LONE DRUID");
                hero.setIconPath("he_lone_druid_full");
                hero.setCategory("Ranged - Carry - Pusher - Jungler - Durable");
                hero.setBiography("Long before the first words of the first histories there rose the druidic Bear Clan. Wise and just they were, and focused in their ways to seek an understanding of the natural order. The arch forces of nature saw this, and so sought the most learned among them. Wise old Sylla, clan justiciar and seer, stepped forward for his kin, and to him was given the Seed with these words: 'When all of the world has dimmed, when civilization has left these lands, when the world is slain and wracked by the endless deserts at the end of ages, plant the Seed.' As he grasped his trust, Sylla felt his years recede and his vitality returned. Vast knowledge burst into his mind. He found himself able to project his very will into reality and, with some concentration, alter his own physical form as well. Yet subtle whispers and cruel ears brought word of the Seed and its power to other peoples, and a terrible war crashed upon the Bear Clan. As his ancestral home burned, Sylla took his burden and fled to the wild places. Ages passed, and time and myth forgot the Bear Clan, forgot Sylla and the Seed, forgot wondrous civilizations that rose and fell in Bear Clan's wake. For millenia Sylla has waited, waited for word from his deities, waited for peace to come to the ever warring realms, waited in exile and in secret for the end of all things and for the conclusion of his sacred commitment, preparing himself always to face and destroy whatever would dare threaten his purpose.\t");
                mHeroDao.insert(hero);

                // MEDUSA
                hero.setName("MEDUSA");
                hero.setIconPath("he_medusa_full");
                hero.setCategory("Ranged - Carry - Disabler - Durable");
                hero.setBiography("Beauty is power. This thought comforted Medusa--the youngest and loveliest of three beautiful Gorgon sisters, born to a sea goddess--because she alone of the sisters was mortal. It comforted her, that is, until the day masked assailants invaded the Gorgon realm and tore the two immortal sisters from their home, unmoved by their beauty or by their tears. One of the invaders seized Medusa as well, but then cast her aside with a disgusted look: 'This one has the mortal stink upon her. We have no use for that which dies.' Humiliated, enraged, Medusa fled to the temple of her mother and cast herself before the goddess, crying, 'You denied me eternal life--therefore I beg you, give me power! Power, so I can dedicate what life I have to rescuing my sisters and avenging this injustice!' After long thought, the goddess granted her daughter's request, allowing Medusa to trade her legendary beauty for a face and form of terrifying strength. Never for a moment has Medusa regretted her choice. She understands that power is the only beauty worth possessing--for only power can change the world.\t");
                mHeroDao.insert(hero);

                // MEEPO
                hero.setName("MEEPO");
                hero.setIconPath("he_meepo_full");
                hero.setCategory("Melee - Carry - Escape - Nuker - Disabler - Initiator - Pusher");
                hero.setBiography("If you ask me, life is all about who you know and what you can find. When you live up in the Riftshadow Ruins, just finding food can be tough. So you need to cut corners, you need to scrounge, you need to know your strengths. Some of the beasts up there can kill you, so you need a way to trap the weak and duck the strong. On the upside, the ruins have history, and history is worth a lot to some people. There used to be a palace there, where they had all these dark rituals. Bad stuff. If you survived the ceremony, they would shatter a crystal and split your soul into pieces. They made great art though! Sculptures and such. Let me tell you: sometimes you stumble onto some of those old carvings. Take a pack full of those to town and sell them, then get yourself food for a few weeks. If luck is really on your side, you might find a Riftshadow crystal. Get it appraised and start asking around. Someone always knows some crazy fool looking for this kind of thing. If all else fails, sell it to a Magus the next time one's in town. They love that stuff. Still, whatever you do, be careful handling those crystals. You do not want one to go off on you. It really hurts.\t");
                mHeroDao.insert(hero);

                // MIRANA
                hero.setName("MIRANA");
                hero.setIconPath("he_mirana_full");
                hero.setCategory("Ranged - Carry - Support - Escape - Nuker - Disabler");
                hero.setBiography("Born to a royal family, a blood princess next in line for the Solar Throne, Mirana willingly surrendered any claim to mundane land or titles when she dedicated herself completely to the service of Selemene, Goddess of the Moon. Known ever since as Princess of the Moon, Mirana prowls the sacred Nightsilver Woods searching for any who would dare poach the sacred luminous lotus from the silvery pools of the Goddess's preserve. Riding on her enormous feline familiar, she is poised, proud and fearless, attuned to the phases of the moon and the wheeling of the greater constellations. Her bow, tipped with sharp shards of lunar ore, draws on the moon's power to charge its arrows of light.\t");
                mHeroDao.insert(hero);

                // MONKEY KING
                hero.setName("MONKEY KING");
                hero.setIconPath("he_monkey_king_full");
                hero.setCategory("Melee - Carry - Escape - Disabler - Initiator");
                hero.setBiography("For 500 years the mountain pressed down upon him, only his head free from the crushing weight of the stonewrought prison the elder gods had summoned to halt his childish rebellion. Moss grew along the lines of his exposed face, tufts of grass sprouted from his ears; his vision was framed in wildflowers reaching from the soil around his cheeks. Most thought him long dead, tormented by the gods for waging war against the heavens until naught but his legend survived. But, as the stories go, the Monkey King cannot die.\n" +
                        "\n" +
                        "So he waited. Until the gods came to offer a chance at absolution, he endured. And when they did come to name the price, Sun Wukong accepted their charge: he would accompany a young acolyte on a secret pilgrimage, protect him from demons and dangers of the road, and guide the man home in possession of a coveted relic. Do that, and humbly obey the human's commands in service to their holy mission, and Wukong would prove himself reformed.\n" +
                        "\n" +
                        "For a change, Sun Wukong fulfilled his oath to the gods with honor, and atoned for the sins of past insurrections. The acolyte, much learned in hardships, was returned to his home temple, relic in hand; and Wukong-finding himself for the first time in proper standing with any gods of consequence-was content for a short while to give up his old thirst for adventure and glory. But the Monkey King was born for mischief...and offending the gods never gets old.\t");
                mHeroDao.insert(hero);

                // OUTWORLD DEVOURER
                hero.setName("OUTWORLD DEVOURER");
                hero.setIconPath("he_obsidian_destroyer_full");
                hero.setCategory("Ranged - Carry - Nuker - Disabler");
                hero.setBiography("One of a lordly and magisterial race, Harbinger prowls the edge of the Void, sole surviving sentry of an outpost on the world at the rim of the abyss. From this jagged crystalline Outworld, forever on guard, he has gazed for eternities into the heavens, alert for any stirring in the bottomless night beyond the stars. Imprinted deep in the shining lattices of his intellect lies a resonant pattern akin to prophecy, a dark music implying that eventually some evil will wake out there, beyond the edges of creation, and turn its attention to our world. With his whole being focused on his vigil, Outworld Devourer paid little attention to events closer in to the sun. But at last the clamor of the Ancients, and a sense of growing threat from within as well as without, sent him winging sunward to visit the plains of war. Harbinger's place in our own prophecies is unambiguous: he must be considered an omen of worse things to come. But his arrival in itself is bad enough. ");
                mHeroDao.insert(hero);

                // PHANTOM ASSASSIN
                hero.setName("PHANTOM ASSASSIN");
                hero.setIconPath("he_phantom_assassin_full");
                hero.setCategory("Melee - Carry - Escape");
                hero.setBiography("Through a process of divination, children are selected for upbringing by the Sisters of the Veil, an order that considers assassination a sacred part of the natural order. The Veiled Sisters identify targets through meditation and oracular utterances. They accept no contracts, and never seem to pursue targets for political or mercenary reasons. Their killings bear no relation to any recognizable agenda, and can seem to be completely random: A figure of great power is no more likely to be eliminated than a peasant or a well digger. Whatever pattern the killings may contain, it is known only to them. They treat their victims as sacrifices, and death at their hand is considered an honor. Raised with no identity except that of their order, any Phantom Assassin can take the place of any other; their number is not known. Perhaps there are many, perhaps there are few. Nothing is known of what lies under the Phantom Veil. Except that this one, from time to time, when none are near enough to hear, is known to stir her veils with the forbidden whisper of her own name: Mortred.\t");
                mHeroDao.insert(hero);

                // PHOENIX
                hero.setName("PHOENIX");
                hero.setIconPath("he_phoenix_full");
                hero.setCategory("Ranged - Support - Nuker - Initiator - Escape - Disabler");
                hero.setBiography("Alone across an untouched darkness gleamed the Keeper's first sun, a singular point of conscious light fated to spread warmth into the empty void. Through aeons beyond count, this blinding beacon set to coalescing its incalculable energy before bursting forth the cataclysmic flare of supernova. From this inferno raced new beacons, star progeny identical to its parent, who journeyed an unlit ocean and settled in constellatory array. In time, they too would be made to propagate through supernova flame. So would this dazzling cycle of birth and rebirth repeat until all skies hewn of Titan toil deigned to twinkle and shine. \n" +
                        "\n" +
                        "By this ageless crucible the star that mortals would come to call Phoenix collapsed into being, and like its ancestors was thrust into an endless cosmos to find a place among its stellar brethren. Yet curiosity toward that which the dimming elders comfort in the darkness consumed the fledgling, and so over long cycles it inquired and studied. It learned that among worlds both whole and broken would soon stir a nexus of remarkable variety locked in an enduring conflict of cosmic consequence, a plane which would find itself in need of more influence than a dying sun's distant rays could provide. Thus this infant son of suns took terrestrial form, eagerly travelling to shine its warmth upon those who may need it most, and perhaps seize upon its solar destiny.\t");
                mHeroDao.insert(hero);

                // PUDGE
                hero.setName("PUDGE");
                hero.setIconPath("he_pudge_full");
                hero.setCategory("Melee - Disabler - Initiator - Durable - Nuker");
                hero.setBiography("In the Fields of Endless Carnage, far to the south of Quoidge, a corpulent figure works tirelessly through the night--dismembering, disembowelling, piling up the limbs and viscera of the fallen that the battlefield might be clear by dawn. In this cursed realm, nothing can decay or decompose; no corpse may ever return to the earth from which it sprang, no matter how deep you dig the grave. Flocked by carrion birds who need him to cut their meals into beak-sized chunks, Pudge the Butcher hones his skills with blades that grow sharper the longer he uses them. Swish, swish, thunk. Flesh falls from the bone; tendons and ligaments part like wet paper. And while he always had a taste for the butchery, over the ages, Pudge has developed a taste for its byproduct as well. Starting with a gobbet of muscle here, a sip of blood there...before long he was thrusting his jaws deep into the toughest of torsos, like a dog gnawing at rags. Even those who are beyond fearing the Reaper, fear the Butcher.\t");
                mHeroDao.insert(hero);

                // QUEEN OF PAIN
                hero.setName("QUEEN OF PAIN");
                hero.setIconPath("he_queenofpain_full");
                hero.setCategory("Ranged - Carry - Nuker - Escape");
                hero.setBiography("The Ecclesiast-King of Elze nursed a desire for pain--forbidden pain. In a less prominent political figure, such desires might be considered unwise, but in a monarch of his stature, to satisfy such thirsts would have threatened the virtue of the Divine Throne itself. Therefore he turned to his dungeon full of demonologists, promising freedom to whoever could summon a personal succubus of torment and bind it entirely to his service. The creature who arrived, Akasha by name, visited upon him such exquisite torments that he named her his Secret Queen, and he began to spend all his spare moments submitting to her clever torments--eventually abdicating all his responsibilities in his pursuit of the painful pleasures that only she could bring. Queen of Pain could bring him to the brink of death, but she was rune-bound to keep him alive. At last the King's neglect of state brought on an uprising. He was dragged from his chamber and hurled from the Tower of Invocations, and at the moment of death, Queen of Pain was let loose into the world, freed from servitude--freed to visit her sufferings on anyone she deigned to notice.\t");
                mHeroDao.insert(hero);

                // SPECTRE
                hero.setName("SPECTRE");
                hero.setIconPath("he_spectre_full");
                hero.setCategory("Melee - Carry - Durable - Escape");
                hero.setBiography("Just as higher states of energy seek a lower level, the Spectre known as Mercurial is a being of intense and violent energy who finds herself irresistibly drawn to scenes of strife as they unfold in the physical world. While her normal spectral state transcends sensory limitations, each time she takes on a physical manifestation, she is stricken by a loss of self--though not of purpose. In the clash of combat, her identity shatters and reconfigures, and she begins to regain awareness. She grasps that she is Mercurial the Spectre--and that all of her Haunts are but shadows of the one true Spectre. Focus comes in the struggle for survival; her true mind reasserts itself; until in the final moments of victory or defeat, she transcends matter and is restored once more to her eternal form.\t");
                mHeroDao.insert(hero);

                // STORM SPIRIT
                hero.setName("STORM SPIRIT");
                hero.setIconPath("he_storm_spirit_full");
                hero.setCategory("Ranged - Carry - Escape - Nuker - Initiator - Disabler");
                hero.setBiography("Storm Spirit is literally a force of nature--the wild power of wind and weather, bottled in human form. And a boisterous, jovial, irrepressible form it is! As jolly as a favorite uncle, he injects every scene with crackling energy. But it was not always thus, and there was tragedy in his creation. Generations ago, in the plains beyond the Wailing Mountains, a good people lay starving in drought and famine. A simple elementalist, Thunderkeg by name, used a forbidden spell to summon the spirit of the storm, asking for rain. Enraged at this mortal's presumption, the Storm Celestial known as Raijin lay waste to the land, scouring it bare with winds and flood. Thunderkeg was no match for the Celestial--at least until he cast a suicidal spell that forged their fates into one: he captured the Celestial in the cage of his own body. Trapped together, Thunderkeg's boundless good humor fused with Raijin's crazed energy, creating the jovial Raijin Thunderkeg, a Celestial who walks the world in physical form. ");
                mHeroDao.insert(hero);

                // TEMPLAR ASSASSIN
                hero.setName("TEMPLAR ASSASSIN");
                hero.setIconPath("he_templar_assassin_full");
                hero.setCategory("Ranged - Carry - Escape");
                hero.setBiography("Lanaya, the Templar Assassin, came to her calling by a path of curious inquiry. Possessed of a scientific bent, she spent her early years engaged in meticulous study of nature's laws--peering into grimoires of magic and alchemy, recreating experiments from charred fragments of the Violet Archives, and memorizing observations of the Keen recordkeepers. Already quiet and secretive by nature, the difficulty of acquiring these objects further reinforced her skills of stealth. Had she been less retiring, she might have become notorious among the guilds as a thief-scholar. Instead her investigations led her into far more obscure corners. As she devoted her furtive talents to unlocking the secrets of the universe, she instead unlocked a secret door that exists in nature itself: the entryway to the most Hidden Temple. The intelligences that waited beyond that portal, proved to be expecting her, and whatever mysteries they revealed in the moment of their discovery was nothing compared to the answers they held out to Lanaya should she continue in their service. She swore to protect the mysteries, but more to the point, in service to the Hidden Temple she satisfies her endless craving for understanding. In the eyes of each foe she expunges, a bit more of the mystery is revealed.\t");
                mHeroDao.insert(hero);

                // VISAGE
                hero.setName("VISAGE");
                hero.setIconPath("he_visage_full");
                hero.setCategory("Ranged - Support - Nuker - Durable - Disabler - Pusher");
                hero.setBiography("Perched atop the entrance to the Narrow Maze sit the looming shapes of sneering gargoyles, the paths into the hereafter forever in their gaze. Beasts and birds, men and monsters, all creatures that die and choose to travel beyond must someday pass beneath their sight. For an untethered spirit, the decision to journey through the veil of death is irrevocable. When chance comes, and by craft or cunning some restless soul escapes their hells and heavens, it is the dreaded gargoyle Visage, the bound form of the eternal spirit Necro'lic, who is dispatched to reclaim them. Ruthless and efficient, unhindered by the principles of death and fatigue, Visage stalks its prey without mercy or end, willingly destroying all which may give shelter to the fugitive essence. That which flaunts the laws of the afterlife may never rest, for while it is true that the dead may be revived, it is only a matter of time before Visage finds and returns them to their proper place.\t");
                mHeroDao.insert(hero);
            }

            // Process all items -----------------------------------
            if (mItemDao.getTableRowCount() == 0) {
                // TOWN PORTAL SCROLL
                Item item = new Item("TOWN PORTAL SCROLL");
                item.setIconPath("it_tpscroll_lg");
                item.setPrice(50);
                item.setAttributes("None.");
                item.setEffects("USE: TELEPORT\n" +
                        "After channeling for 3 seconds, teleports you to a target friendly building. \n" +
                        "\n" +
                        "Double-click to teleport to your team's base fountain. ");
                item.setDescription("What a hero truly needs.");
                mItemDao.insert(item);

                // CLARITY
                item.setName("CLARITY");
                item.setIconPath("it_clarity_lg");
                item.setPrice(50);
                item.setAttributes("None.");
                item.setEffects("USE: REPLENISH\n" +
                        "Grants 3 mana regeneration to the target for 50 seconds.\n" +
                        "\n" +
                        "If the unit is attacked by an enemy hero or Roshan, the effect is lost.\n" +
                        "\n" +
                        "Range: 250 ");
                item.setDescription("Clear water that enhances the ability to meditate.");
                mItemDao.insert(item);

                // FAERIE FIRE
                item.setName("FAERIE FIRE");
                item.setIconPath("it_faerie_fire_lg");
                item.setPrice(70);
                item.setAttributes("+ 2 Damage \n" +
                        "Cooldown 5");
                item.setEffects("USE: IMBUE\n" +
                        "Instantly restores 85 HP. ");
                item.setDescription("The ethereal flames from the ever-burning ruins of Kindertree ignite across realities. ");
                mItemDao.insert(item);

                // SMOKE OF DECEIT
                item.setName("SMOKE OF DECEIT");
                item.setIconPath("it_smoke_of_deceit_lg");
                item.setPrice(80);
                item.setAttributes("Cooldown 1\n");
                item.setEffects("USE: DISGUISE\n" +
                        "The user and all nearby allied player-controlled units in a 1025 radius gain Deceit and 15% bonus movement speed for 35 seconds. \n" +
                        "\n" +
                        "Attacking, or moving within 1025 range of an enemy hero or tower, will break the invisibility. \n" +
                        "\n" +
                        "Disguise grants invisibility that is immune to True Sight. ");
                item.setDescription("The charlatan wizard Myrddin's only true contribution to the arcane arts. ");
                mItemDao.insert(item);

                // OBSERVER WARD
                item.setName("OBSERVER WARD");
                item.setIconPath("it_ward_observer_lg");
                item.setPrice(75);
                item.setAttributes("Cooldown 1\n");
                item.setEffects("USE: PLANT\n" +
                        "Plants an Observer Ward, an invisible watcher that gives ground vision in a 1600 radius to your team. Lasts 6 minutes.\n" +
                        "\n" +
                        "Hold Control to give one Observer Ward to an allied hero.\n" +
                        "\n" +
                        "Range: 500 ");
                item.setDescription("A form of half-sentient plant, often cultivated by apprentice wizards. ");
                mItemDao.insert(item);

                // SENTRY WARD
                item.setName("SENTRY WARD");
                item.setIconPath("it_ward_sentry_lg");
                item.setPrice(100);
                item.setAttributes("Cooldown 1\n");
                item.setEffects("USE: PLANT\n" +
                        "Plants a Sentry Ward, an invisible watcher that grants True Sight, the ability to see invisible enemy units and wards, to any existing allied vision within a 850 radius.\n" +
                        "Duration: 4 minutes.\n" +
                        "\n" +
                        "Does not grant ground vision.\n" +
                        "Hold Control to give one Sentry Ward to an allied hero.\n" +
                        "\n" +
                        "Range: 500 ");
                item.setDescription("A form of plant originally grown in the garden of a fearful king. ");
                mItemDao.insert(item);

                // ENCHANTED MANGO
                item.setName("ENCHANTED MANGO");
                item.setIconPath("it_enchanted_mango_lg");
                item.setPrice(70);
                item.setAttributes("+ 0.5 HP Regeneration ");
                item.setEffects("USE: EAT MANGO\n" +
                        "Instantly restore 125 mana.\n" +
                        "\n" +
                        "Range: 400 ");
                item.setDescription("The bittersweet flavors of Jidi Isle are irresistible to amphibians. ");
                mItemDao.insert(item);

                // HEALING SALVE
                item.setName("HEALING SALVE");
                item.setIconPath("it_flask_lg");
                item.setPrice(110);
                item.setAttributes("None.\n");
                item.setEffects("USE: SALVE\n" +
                        "Grants 50 health regeneration to the target for 8 seconds.\n" +
                        "\n" +
                        "If the unit is attacked by an enemy hero or Roshan, the effect is lost.\n" +
                        "\n" +
                        "Range: 250 ");
                item.setDescription("A magical salve that can quickly mend even the deepest of wounds. ");
                mItemDao.insert(item);

                // TANGO
                item.setName("TANGO");
                item.setIconPath("it_tango_lg");
                item.setPrice(90);
                item.setAttributes("None.\n");
                item.setEffects("USE: DEVOUR\n" +
                        "Consume a target tree to gain 7 health regeneration for 16 seconds. Consuming a ward or Ironwood Tree doubles the heal amount.\n" +
                        "\n" +
                        "Comes with 3 charges. Can be used on an allied hero to give them one Tango.\n" +
                        "\n" +
                        "Range: 165\n" +
                        "Ward Range: 450 ");
                item.setDescription("Forage to survive on the battlefield. ");
                mItemDao.insert(item);

                // TOME OF KNOWLEDGE
                item.setName("TOME OF KNOWLEDGE");
                item.setIconPath("it_tome_of_knowledge_lg");
                item.setPrice(150);
                item.setAttributes("None.\n");
                item.setEffects("USE: ENLIGHTEN\n" +
                        "Grants you 700 experience plus 135 per tome consumed by your team.\n" +
                        "\n" +
                        "Tomes Used By Team: %customval_team_tomes_used% ");
                item.setDescription("That which raises beast to man and man to god. ");
                mItemDao.insert(item);

                // DUST OF APPEARANCE
                item.setName("DUST OF APPEARANCE");
                item.setIconPath("it_dust_lg");
                item.setPrice(180);
                item.setAttributes("Cooldown 30");
                item.setEffects("USE: REVEAL\n" +
                        "Reveals and slows invisible Heroes by 20 in a 1050 radius around the caster for 12 seconds. ");
                item.setDescription("One may hide visage, but never volume. ");
                mItemDao.insert(item);

                // COURIER
                item.setName("COURIER");
                item.setIconPath("it_courier_lg");
                item.setPrice(50);
                item.setAttributes("None.\n");
                item.setEffects("USE: DEPLOY COURIER\n" +
                        "Deploys a creature to carry items to and from your team's base. ");
                item.setDescription("Losing the courier is punishable by death. ");
                mItemDao.insert(item);

                // BOTTLE
                item.setName("BOTTLE");
                item.setIconPath("it_bottle_lg");
                item.setPrice(650);
                item.setAttributes("None.\n");
                item.setEffects("ACTIVE: REGENERATE\n" +
                        "Consumes a charge to restore 100 health and 50 mana over 2.5 seconds.\n" +
                        "\n" +
                        "Range: 350\n" +
                        "\n" +
                        "Healing stops if attacked by an enemy hero or Roshan. Bottle can be refilled at your team's fountain. Runes can be stored in the Bottle for later use, and are activated automatically after 2 minutes. ");
                item.setDescription("An old bottle that survived the ages, the contents placed inside become enchanted. ");
                mItemDao.insert(item);

                // BUTTERFLY
                item.setName("BUTTERFLY");
                item.setIconPath("it_butterfly_lg");
                item.setPrice(5475);
                item.setAttributes("+ 35 Agility\n" +
                        "+ 25 Damage\n" +
                        "+ 35% Evasion\n" +
                        "+ 30 Attack Speed");
                item.setEffects("ACTIVE: FLUTTER\n" +
                        "Grants 35% additional movement speed for 2 seconds. ");
                item.setDescription("Only the mightiest and most experienced of warriors can wield the Butterfly, but it provides incredible dexterity in combat. ");
                mItemDao.insert(item);

                // CRYSTALYS
                item.setName("CRYSTALYS");
                item.setIconPath("it_lesser_crit_lg");
                item.setPrice(2130);
                item.setAttributes("+ 30 Damage ");
                item.setEffects("PASSIVE: CRITICAL STRIKE\n" +
                        "Grants each attack a 20% chance to deal 175% damage. ");
                item.setDescription("A blade forged from rare crystals, it seeks weak points in enemy armor. ");
                mItemDao.insert(item);

                // SKULL BASHER
                item.setName("SKULL BASHER\n");
                item.setIconPath("it_basher_lg");
                item.setPrice(3050);
                item.setAttributes("+ 10 Strength \n" +
                        "Cooldown2");
                item.setEffects("PASSIVE: BASH\n" +
                        "Grants melee heroes a 25% chance on hit to stun the target for 1.5 seconds and deal 100 bonus damage. Bash pierces evasion. Bash chance for ranged heroes is 10%. ");
                item.setDescription("A feared weapon in the right hands, this maul's ability to shatter the defenses of its opponents should not be underestimated. ");
                mItemDao.insert(item);

                // BATTLE FURY
                item.setName("BATTLE FURY\n");
                item.setIconPath("it_bfury_lg");
                item.setPrice(4400);
                item.setAttributes("+ 45 Damage\n" +
                        "+ 6 HP Regeneration\n" +
                        "+ 2.25 Mana Regeneration \n" +
                        "Cooldown4\n");
                item.setEffects("ACTIVE: CHOP TREE/WARD\n" +
                        "Destroy a target tree or ward.\n" +
                        "\n" +
                        "Tree Cast Range: 350\n" +
                        "Ward Cast Range: 450 \n" +
                        "PASSIVE: QUELL\n" +
                        "Increases attack damage against non-hero units by 60% for melee heroes, and 25% for ranged. \n" +
                        "PASSIVE: CLEAVE\n" +
                        "Deals 40% of attack damage in a cone up to 625 around the target. (Melee Only) ");
                item.setDescription("The bearer of this mighty axe gains the ability to cut down swaths of enemies at once. ");
                mItemDao.insert(item);

                // DAEDALUS
                item.setName("DAEDALUS");
                item.setIconPath("it_greater_crit_lg");
                item.setPrice(5330);
                item.setAttributes("+ 80 Damage ");
                item.setEffects("PASSIVE: CRITICAL STRIKE\n" +
                        "Grants each attack a 30% chance to deal 235% damage. ");
                item.setDescription("A weapon of incredible power that is difficult for even the strongest of warriors to control. ");
                mItemDao.insert(item);

                // METEOR HAMMER
                item.setName("METEOR HAMMER");
                item.setIconPath("it_meteor_hammer_lg");
                item.setPrice(2625);
                item.setAttributes("+ 12 Strength\n" +
                        "+ 12 Intelligence\n" +
                        "+ 4 HP Regeneration\n" +
                        "+ 1.5 Mana Regeneration \n" +
                        "Mana Cost125Cooldown28\n" +
                        "\n");
                item.setEffects("ACTIVE: METEOR HAMMER\n" +
                        "CHANNELED - After a successful channel, summons a meteor that strikes a 300 AoE, stunning enemies for 2 seconds and dealing impact damage. Continues to deal damage over time to enemies units and buildings for 6 seconds.\n" +
                        "\n" +
                        "Building Impact Damage: 75 \n" +
                        "Building Over Time Damage: 50 \n" +
                        "\n" +
                        "Non-Building Impact Damage: 150 \n" +
                        "Non-Building Over Time Damage: 90 \n" +
                        "\n" +
                        "Channel Duration: 2.5 seconds.\n" +
                        "Landing Time: 0.5 seconds. ");
                item.setDescription("An enchanted hammer forged of metals discovered in the ruins of a village destroyed by a great star storm.");
                mItemDao.insert(item);

                // MONKEY KING BAR
                item.setName("MONKEY KING BAR");
                item.setIconPath("it_monkey_king_bar_lg");
                item.setPrice(4175);
                item.setAttributes("+ 10 Attack Speed ");
                item.setEffects("PASSIVE: PIERCE\n" +
                        "Grants each attack a 75% chance to pierce through evasion and deal 100 bonus pure damage. ");
                item.setDescription("A powerful staff used by a master warrior. ");
                mItemDao.insert(item);

                // RADIANCE
                item.setName("RADIANCE");
                item.setIconPath("it_radiance_lg");
                item.setPrice(5150);
                item.setAttributes("+ 65 Damage ");
                item.setEffects("TOGGLE: BURN\n" +
                        "When active, scorches enemies for 60 damage per second, and causes them to miss 17% of their attacks.\n" +
                        "\n" +
                        "Radius: 700 ");
                item.setDescription("A divine weapon that causes damage and a bright burning effect that lays waste to nearby enemies. ");
                mItemDao.insert(item);

                // DIVINE RAPIER
                item.setName("DIVINE RAPIER");
                item.setIconPath("it_rapier_lg");
                item.setPrice(6000);
                item.setAttributes("+ 330 Damage ");
                item.setEffects("PASSIVE: EVERLASTING\n" +
                        "Dropped on death, and cannot be destroyed. \n" +
                        "\n" +
                        "Becomes unusable if picked up by an ally of its owner until it is returned to its owner. It is immediately usable by anybody if an enemy of the owner picks it up and is killed. A dropped Rapier cannot be picked up by a courier. ");
                item.setDescription("So powerful, it cannot have a single owner. ");
                mItemDao.insert(item);

                // REFRESHER ORB
                item.setName("REFRESHER ORB");
                item.setIconPath("it_refresher_lg");
                item.setPrice(5100);
                item.setAttributes("+ 11 HP Regeneration\n" +
                        "+ 5 Mana Regeneration \n" +
                        "Mana Cost375Cooldown");
                item.setEffects("ACTIVE: RESET COOLDOWNS\n" +
                        "Resets the cooldowns of all your items and abilities. ");
                item.setDescription("A powerful artifact created for wizards. ");
                mItemDao.insert(item);

                // SANGE AND YASHA
                item.setName("SANGE AND YASHA");
                item.setIconPath("it_sange_and_yasha_lg");
                item.setPrice(4300);
                item.setAttributes("+ 16 Damage\n" +
                        "+ 16 Strength\n" +
                        "+ 16 Agility\n" +
                        "+ 16 Attack Speed\n" +
                        "+ 16% Movement Speed ");
                item.setEffects("PASSIVE: GREATER MAIM\n" +
                        "Each attack has a 40% chance to reduce enemy hero movement speed by 26% and attack speed by 26 when used by a melee hero. Reduces enemy hero movement by 13% and attack speed by 13 when used by a ranged hero. Effect lasts 5 seconds. ");
                item.setDescription("Sange and Yasha, when attuned by the moonlight and used together, become a very powerful combination. ");
                mItemDao.insert(item);

                // SANGE
                item.setName("SANGE");
                item.setIconPath("it_sange_lg");
                item.setPrice(2150);
                item.setAttributes("+ 10 Damage\n" +
                        "+ 16 Strength ");
                item.setEffects("PASSIVE: LESSER MAIM\n" +
                        "Each attack has a 40% chance to reduce enemy hero movement speed by 26% and attack speed by 26 when used by a melee hero. Reduces enemy hero movement by 13% and attack speed by 13 when used by a ranged hero. Effect lasts 5 seconds. ");
                item.setDescription("Sange is an unusually accurate weapon, seeking weak points automatically. ");
                mItemDao.insert(item);

                // SCYTHE OF VYSE
                item.setName("SCYTHE OF VYSE");
                item.setIconPath("it_sheepstick_lg");
                item.setPrice(5700);
                item.setAttributes("+ 10 Strength\n" +
                        "+ 10 Agility\n" +
                        "+ 35 Intelligence\n" +
                        "+ 2.25 Mana Regeneration \n" +
                        "Mana Cost100");
                item.setEffects("ACTIVE: HEX\n" +
                        "Turns a target unit into a harmless critter for 3.5 seconds. The target has a base movement speed of 140 and will be silenced, muted, and disarmed.\n" +
                        "Instantly destroys illusions.\n" +
                        "\n" +
                        "Range: 800 ");
                item.setDescription("The most guarded relic among the cult of Vyse, it is the most coveted weapon among magi. ");
                mItemDao.insert(item);

                // EYE OF SKADI
                item.setName("EYE OF SKADI");
                item.setIconPath("it_skadi_lg");
                item.setPrice(5500);
                item.setAttributes("+ 25 All Attributes\n" +
                        "+ 225 Health\n" +
                        "+ 250 Mana ");
                item.setEffects("PASSIVE: COLD ATTACK\n" +
                        "Attacks lower enemy attack speed by 45 and movement speed by 35%. \n" +
                        "\n" +
                        "Lasts 5 seconds when used by melee heroes, and 2.5 seconds when used by ranged. ");
                item.setDescription("Extremely rare artifact, guarded by the azure dragons. ");
                mItemDao.insert(item);

                // YASHA
                item.setName("YASHA");
                item.setIconPath("it_yasha_lg");
                item.setPrice(2150);
                item.setAttributes("+ 16 Agility\n" +
                        "+ 10 Attack Speed\n" +
                        "+ 8% Movement Speed ");
                item.setEffects("Yasha-based movement speed bonuses from multiple items do not stack");
                item.setDescription("Yasha is regarded as the swiftest weapon ever created. ");
                mItemDao.insert(item);
            }
            return null;
        }
    }
}
