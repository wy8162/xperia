const travel = (m, n, memo = {}) => {
  const key = m + ',' + n;

  if (key in memo) return memo[key];
  if (m === 1 && n === 1) return 1;
  if (m === 0 || n === 0) return 0;

  memo[key] = travel(m - 1, n, memo) + travel(m, n - 1, memo);

  return memo[key];
}

function assert(expected, actual, description = "!") {
  var result = expected === actual ? "pass" : "fail";
  console.log(`${result} : ${expected} vs ${actual} - ${description}`);
};

assert(travel(18, 18), 2333606220)