import ray
import logging
import random
import time
if ray.is_initialized:
    ray.shutdown()
ray.init(logging_level=logging.ERROR)

#rozwiązanie oparte zostało na ogólnodostępnym przykładzie obliczania pi podanym w dokumentacji Ray'a dostępnej z linku podanego w treści zadania
@ray.remote
class ProgressActor:
    def __init__(self, number_of_samples):
        self.number_of_samples = number_of_samples
        self.number_of_samples_processed_per_task = {}

    def report_progress(self, task_id, number_of_samples_processed):
        self.number_of_samples_processed_per_task[task_id] = number_of_samples_processed

    def get_progress(self):
        return sum(self.number_of_samples_processed_per_task.values()) / self.number_of_samples


@ray.remote
def sampling_task(samples_to_process, task_id, progress_actor):
    number_of_points_inside = 0
    for i in range(samples_to_process):
        x, y = random.uniform(-1, 1), random.uniform(-1, 1)
        if x**2+y**2 <= 1:
            number_of_points_inside += 1

        if (i + 1) % 1000000 == 0:
            # make actor report the progress every million samples
            progress_actor.report_progress.remote(task_id, i + 1)

    # make actor report the final progress.
    progress_actor.report_progress.remote(task_id, samples_to_process)
    return number_of_points_inside


number_of_tasks = 10
samples_per_task = 10000000
number_of_samples = number_of_tasks * samples_per_task

progress_actor = ProgressActor.remote(number_of_samples)

results = [sampling_task.remote(samples_per_task, i, progress_actor) for i in range(number_of_tasks)]

while True:
    progress = ray.get(progress_actor.get_progress.remote())
    print(f"Progress: {int(progress * 100)}%")
    if progress == 1:
        break
    time.sleep(1)
all_points_inside = sum(ray.get(results))

pi = (all_points_inside * 4) / number_of_samples

print(f"Computed value of π is: {pi}")