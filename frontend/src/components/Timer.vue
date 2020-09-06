<template>
  <div>Через {{ formattedTimeLeft }} начнется чат со следующим участником</div>
</template>

<script>
export default {
  name: 'Timer',
  props: {
    timelimit: Number
  },

  data() {
    return {
      timePassed: 0,
      timerInterval: null,
    };
  },

  computed: {
    formattedTimeLeft() {
      const timeLeft = this.timeLeft;
      const minutes = Math.floor(timeLeft / 60);
      let seconds = timeLeft % 60;

      if (seconds < 10) {
        seconds = `0${seconds}`;
      }

      return `${minutes}:${seconds}`;
    },

    timeLeft() {
      return this.timelimit - this.timePassed;
    },

    timeFraction() {
      const rawTimeFraction = this.timeLeft / this.timelimit;
      return rawTimeFraction - (1 / this.timelimit) * (1 - rawTimeFraction);
    },
  },

  watch: {
    timeLeft(newValue) {
      if (newValue === 0) {
        this.onTimesUp();
      }
    },
  },

  mounted() {
    this.startTimer();
  },

  methods: {
    onTimesUp() {
      clearInterval(this.timerInterval);
      this.$emit("timesup");
    },
    startTimer() {
      this.timerInterval = setInterval(() => (this.timePassed += 1), 1000);
    },
  },
};
</script>
