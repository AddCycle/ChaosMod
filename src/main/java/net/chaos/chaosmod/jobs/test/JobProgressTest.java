package net.chaos.chaosmod.jobs.test;

public class JobProgressTest {

//	@Test
//    void expToNextLevel_scalesCorrectly() {
//        JobProgress p = new JobProgress(1, 0);
//        assertEquals(150, p.getExpToNextLevel(1));
//        assertEquals(200, p.getExpToNextLevel(2));
//        assertEquals(250, p.getExpToNextLevel(3));
//    }
//
//    @Test
//    void addExp_doesNotExceedGoalOnClamp() {
//        // incrementTaskProgress clamps to task.goal
//        // test the math in isolation
//        int current = 95;
//        int amount = 20;
//        int goal = 100;
//        int result = Math.min(current + amount, goal);
//        assertEquals(100, result);
//    }
////
////    @Test
////    void sharedTaskProgress_nbtRoundtrip() {
////        SharedTaskProgress original = new SharedTaskProgress();
////        original.getProgressMap()
////                .computeIfAbsent("farmer", k -> new HashMap<>())
////                .put("harvest_wheat", 42);
////
////        NBTTagCompound nbt = original.toNBT();
////
////        SharedTaskProgress loaded = new SharedTaskProgress();
////        loaded.fromNBT(nbt);
////
////        assertEquals(42, loaded.getTaskProgress("farmer", "harvest_wheat"));
////    }
//
//    @Test
//    void jobTask_jsonRoundtrip() {
//        JsonObject json = new JsonObject();
//        json.addProperty("id", "harvest_wheat");
//        json.addProperty("name", "Harvest Wheat");
//        json.addProperty("description", "desc");
//        json.addProperty("type", "harvest");
//        json.add("target", new JsonObject());
//        json.addProperty("goal", 100);
//        json.addProperty("rewardExp", 200);
//        json.addProperty("shared", true);
//
//        JobTask task = JobTask.fromJson(json);
//        JsonObject out = task.toJson();
//
//        assertEquals("harvest_wheat", task.id); // or with prefix
//        assertEquals(100, task.goal);
//        assertTrue(task.shared);
//        assertEquals(task.goal, out.get("goal").getAsInt());
//    }
}