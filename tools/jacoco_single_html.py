#!/usr/bin/env python3
"""Рендерит один самодостаточный HTML-отчёт о покрытии из JaCoCo XML.

Официальный HTML JaCoCo многофайловый (страница на класс). Этот скрипт
собирает единую страницу со сводкой и таблицей покрытия по пакетам/классам.

Использование:
    python3 tools/jacoco_single_html.py \
        app/build/reports/jacoco/testDevDebugUnitTestCoverage/testDevDebugUnitTestCoverage.xml \
        > reports/coverage.html
"""
import sys
import html
import xml.etree.ElementTree as ET

root = ET.parse(sys.argv[1]).getroot()


def counters(el):
    d = {}
    for c in el.findall('counter'):
        t = c.get('type')
        cov = int(c.get('covered'))
        mis = int(c.get('missed'))
        d[t] = (cov, cov + mis)
    return d


def pct(pair):
    cov, tot = pair
    return round(100 * cov / tot) if tot else 100


def bar(p):
    color = '#3fb950' if p >= 80 else ('#d29922' if p >= 50 else '#f85149')
    return (f'<div class="bar"><div class="fill" style="width:{p}%;background:{color}"></div>'
            f'<span class="lbl">{p}%</span></div>')


overall = counters(root)
rows_pkg = []
rows_cls = []
for pkg in root.findall('package'):
    pname = pkg.get('name').replace('/', '.')
    rows_pkg.append((pname, counters(pkg)))
    for cls in pkg.findall('class'):
        cname = cls.get('name').split('/')[-1]
        if '$' in cname:  # пропускаем синтетические вложенные лямбды
            continue
        rows_cls.append((pname, cname, counters(cls)))


def cell(c, key):
    return bar(pct(c[key])) if key in c else '<span class="na">—</span>'


def table(rows, first_hdr, is_cls):
    out = ['<table><thead><tr>', f'<th>{first_hdr}</th>']
    if is_cls:
        out.append('<th>Класс</th>')
    out.append('<th>Строки</th><th>Ветвления</th><th>Методы</th></tr></thead><tbody>')
    for r in rows:
        if is_cls:
            pname, cname, c = r
            out.append(f'<tr><td class="pkg">{html.escape(pname)}</td><td class="cls">{html.escape(cname)}</td>')
        else:
            pname, c = r
            out.append(f'<tr><td class="pkg">{html.escape(pname)}</td>')
        out.append(f'<td>{cell(c, "LINE")}</td><td>{cell(c, "BRANCH")}</td><td>{cell(c, "METHOD")}</td></tr>')
    out.append('</tbody></table>')
    return ''.join(out)


rows_cls.sort(key=lambda r: pct(r[2].get('LINE', (0, 0))), reverse=True)
rows_pkg.sort(key=lambda r: r[0])

summary_cards = ''.join(
    f'<div class="card"><div class="k">{name}</div><div class="v">{pct(overall[key])}%</div>'
    f'<div class="s">{overall[key][0]}/{overall[key][1]}</div></div>'
    for name, key in [('Строки', 'LINE'), ('Ветвления', 'BRANCH'), ('Методы', 'METHOD'), ('Классы', 'CLASS')]
    if key in overall)

print(f"""<!doctype html><html lang="ru"><head><meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Отчёт о покрытии — JaCoCo</title>
<style>
:root{{color-scheme:light dark}}
body{{font:14px/1.5 -apple-system,Segoe UI,Roboto,sans-serif;margin:0;padding:24px;
background:#0d1117;color:#e6edf3}}
@media (prefers-color-scheme:light){{body{{background:#fff;color:#1f2328}}}}
h1{{font-size:20px;margin:0 0 4px}} .sub{{opacity:.6;margin:0 0 20px}}
.cards{{display:flex;gap:12px;flex-wrap:wrap;margin-bottom:24px}}
.card{{border:1px solid #30363d;border-radius:10px;padding:12px 18px;min-width:110px}}
@media (prefers-color-scheme:light){{.card{{border-color:#d0d7de}}}}
.card .k{{opacity:.6;font-size:12px}} .card .v{{font-size:26px;font-weight:700}}
.card .s{{opacity:.5;font-size:12px}}
h2{{font-size:15px;margin:24px 0 8px}}
.wrap{{overflow-x:auto}}
table{{border-collapse:collapse;width:100%;min-width:640px}}
th,td{{text-align:left;padding:6px 10px;border-bottom:1px solid #21262d;font-size:13px}}
@media (prefers-color-scheme:light){{th,td{{border-bottom-color:#eaecef}}}}
th{{opacity:.6;font-weight:600}}
.pkg{{opacity:.7;font-family:ui-monospace,monospace;font-size:12px}}
.cls{{font-weight:600}}
.bar{{position:relative;background:#21262d;border-radius:4px;height:16px;width:120px;overflow:hidden}}
@media (prefers-color-scheme:light){{.bar{{background:#eaecef}}}}
.fill{{position:absolute;left:0;top:0;bottom:0}}
.lbl{{position:relative;font-size:11px;padding-left:6px;line-height:16px;mix-blend-mode:difference;color:#fff}}
.na{{opacity:.4}}
</style></head><body>
<h1>Отчёт о тестовом покрытии (JaCoCo)</h1>
<p class="sub">Юнит-тесты <code>com.example.moviedb.tests.*</code> · вариант devDebug · сгенерировано из JaCoCo XML</p>
<div class="cards">{summary_cards}</div>
<h2>По пакетам</h2><div class="wrap">{table(rows_pkg, 'Пакет', False)}</div>
<h2>По классам</h2><div class="wrap">{table(rows_cls, 'Пакет', True)}</div>
</body></html>""")
