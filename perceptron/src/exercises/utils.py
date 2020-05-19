import numpy as np


def run_multiple_times_and_collect_errors(number_of_times, run_training, test_to_training_ratio, data_in, data_out):
    errors = []
    for i in range(number_of_times):
        print(" ".join(["Iteration:", str(i)]))
        # Iteration
        randomize = np.random.permutation(len(data_in))
        data_in = np.array(data_in)[randomize]
        data_out = np.array(data_out)[randomize]
        cut_index = int(np.floor(len(data_in)*test_to_training_ratio))
        training_in = data_in[:cut_index]
        training_out = data_out[:cut_index]
        test_in = data_in[cut_index:]
        test_out = data_out[cut_index:]
        error = run_training((training_in.tolist(), training_out.tolist()), (test_in.tolist(), test_out.tolist()))
        errors.append(error)
    return errors
