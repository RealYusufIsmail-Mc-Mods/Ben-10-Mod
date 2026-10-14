const search = document.querySelector("#item-search");

if (search) {
  const rows = [...document.querySelectorAll("[data-item-row]")];
  const count = document.querySelector("#item-count");
  const emptyState = document.querySelector("#item-empty");

  function filterItems() {
    const query = search.value.trim().toLocaleLowerCase();
    let visible = 0;

    for (const row of rows) {
      const matches = row.dataset.search.includes(query);
      row.hidden = !matches;
      visible += Number(matches);
    }

    count.textContent = `${visible} of ${rows.length} entries`;
    emptyState.hidden = visible !== 0;
  }

  search.addEventListener("input", filterItems);
  filterItems();
}
